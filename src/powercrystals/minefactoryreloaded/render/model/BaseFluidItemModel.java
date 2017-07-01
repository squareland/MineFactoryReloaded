package powercrystals.minefactoryreloaded.render.model;

import codechicken.lib.util.TransformUtils;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.vecmath.Matrix4f;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class BaseFluidItemModel implements IModel, IModelCustomData {

	// minimal Z offset to prevent depth-fighting
	private static final float NORTH_Z_FLUID = 7.498f / 16f;
	private static final float SOUTH_Z_FLUID = 8.502f / 16f;

	private final Fluid fluid;

	private ResourceLocation base;
	private ResourceLocation mask;

	public BaseFluidItemModel(ResourceLocation base, ResourceLocation mask) {

		this(null, base, mask);
	}

	public BaseFluidItemModel(Fluid fluid, ResourceLocation base, ResourceLocation mask) {

		this.fluid = fluid;
		this.base = base;
		this.mask = mask;
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {

		return ImmutableList.of();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {

		return ImmutableList.of(base, mask);
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {

		ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformMap = IPerspectiveAwareModel.MapWrapper.getTransforms(state);

		TRSRTransformation transform = state.apply(Optional.absent()).or(TRSRTransformation.identity());
		TextureAtlasSprite fluidSprite = null;
		ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

		if (fluid != null) {
			fluidSprite = bakedTextureGetter.apply(fluid.getStill());
		}

		IBakedModel model = (new ItemLayerModel(ImmutableList.of(base))).bake(state, format, bakedTextureGetter);
		builder.addAll(model.getQuads(null, null, 0));

		if (fluidSprite != null) {
			TextureAtlasSprite liquid = bakedTextureGetter.apply(mask);
			builder.addAll(ItemTextureQuadConverter.convertTexture(format, transform, liquid, fluidSprite, NORTH_Z_FLUID, EnumFacing.NORTH, fluid.getColor()));
			builder.addAll(ItemTextureQuadConverter.convertTexture(format, transform, liquid, fluidSprite, SOUTH_Z_FLUID, EnumFacing.SOUTH, fluid.getColor()));
		}

		return createBakedModel(builder.build(), fluidSprite, format, Maps.immutableEnumMap(transformMap));
	}

	protected abstract IBakedModel createBakedModel(ImmutableList<BakedQuad> quads, TextureAtlasSprite particle, VertexFormat format, 
			ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms);

	@Override
	public IModelState getDefaultState() {

		return TransformUtils.DEFAULT_ITEM;
	}

	@Override
	public BaseFluidItemModel process(ImmutableMap<String, String> customData) {

		String fluidName = customData.get("fluid");
		Fluid fluid = FluidRegistry.getFluid(fluidName);

		if (fluid == null) {
			fluid = this.fluid;
		}

		// create new model with correct liquid
		return createModel(fluid);
	}

	protected abstract BaseFluidItemModel createModel(Fluid fluid);

	protected abstract static class BakedFluidItemOverrideHandler extends ItemOverrideList {


		protected BakedFluidItemOverrideHandler() {

			super(ImmutableList.of());
		}

		@Override
		public IBakedModel handleItemState(IBakedModel originalModel, @Nonnull ItemStack stack, World world, EntityLivingBase entity) {

			BakedFluidItem model = (BakedFluidItem) originalModel;

			String name = getFluidNameFromStack(stack);

			if (name == null || name.isEmpty()) {
				return originalModel;
			}

			if (!model.cache.containsKey(name)) {
				IModel parent = model.parent.process(ImmutableMap.of("fluid", name));
				Function<ResourceLocation, TextureAtlasSprite> textureGetter
						= location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());

				IBakedModel bakedModel = parent.bake(new SimpleModelState(model.transforms), model.format, textureGetter);
				model.cache.put(name, bakedModel);
				return bakedModel;
			}

			return model.cache.get(name);
		}

		protected abstract String getFluidNameFromStack(@Nonnull ItemStack stack);
	}

	protected static final class BakedFluidItem implements IPerspectiveAwareModel {

		private final BaseFluidItemModel parent;
		// FIXME: guava cache?
		private final Map<String, IBakedModel> cache; // contains all the baked models since they'll never change
		private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
		private final ImmutableList<BakedQuad> quads;
		private final TextureAtlasSprite particle;
		private final VertexFormat format;
		private final BakedFluidItemOverrideHandler overrideHandler;
		
		public BakedFluidItem(BaseFluidItemModel parent, ImmutableList<BakedQuad> quads, TextureAtlasSprite particle, VertexFormat format, 
				ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms, Map<String, IBakedModel> cache, 
				BakedFluidItemOverrideHandler overrideHandler) {

			this.quads = quads;
			this.particle = particle;
			this.format = format;
			this.parent = parent;
			this.transforms = transforms;
			this.cache = cache;
			this.overrideHandler = overrideHandler;
		}

		@Override
		public ItemOverrideList getOverrides() {

			return overrideHandler;
		}

		@Override
		public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {

			return IPerspectiveAwareModel.MapWrapper.handlePerspective(this, transforms, cameraTransformType);
		}

		@Override
		public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {

			if (side == null) {
				return quads;
			}
			return ImmutableList.of();
		}

		public boolean isAmbientOcclusion() {

			return true;
		}

		public boolean isGui3d() {

			return false;
		}

		public boolean isBuiltInRenderer() {

			return false;
		}

		public TextureAtlasSprite getParticleTexture() {

			return particle;
		}

		public ItemCameraTransforms getItemCameraTransforms() {

			return ItemCameraTransforms.DEFAULT;
		}
	}
}
