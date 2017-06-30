package powercrystals.minefactoryreloaded.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.MFRFluids;

public class ItemMFRBucketMilk extends ItemBucketMilk {

	private ItemBucket bucketDelegate;

	public ItemMFRBucketMilk() {

		super();
		bucketDelegate = new ItemBucket(MFRFluids.milkLiquid);
		setUnlocalizedName("mfr.bucket.milk");
		setRegistryName(MineFactoryReloadedCore.modId + ":milk_bucket");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {

		return bucketDelegate.onItemRightClick(worldIn, playerIn, hand);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {

		return bucketDelegate.initCapabilities(stack, nbt);
	}
}
