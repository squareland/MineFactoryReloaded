package powercrystals.minefactoryreloaded.gui.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.gui.container.ContainerBag;

import static cofh.core.init.CoreProps.PATH_GUI_STORAGE;

@SideOnly(Side.CLIENT)
public class GUIBag extends GuiContainer {
    private static final ResourceLocation guiTextures = new ResourceLocation(PATH_GUI_STORAGE + "storage_5.png");
    private final ContainerBag bag;

    public GUIBag(ContainerBag container) {
        super(container);
        this.bag = container;
        this.allowUserInput = false;
        this.ySize = 148;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        fontRenderer.drawString(this.bag.getInventoryName(), 8, 6, 4210752);
        fontRenderer.drawString(I18n.translateToLocal("container.inventory"), 8, this.ySize - 96 + 3, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawDefaultBackground();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}
