package powercrystals.minefactoryreloaded.gui.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import powercrystals.minefactoryreloaded.MFRProps;

import javax.annotation.Nonnull;

public class GuiNeedlegun extends GuiContainer {
    private static final ResourceLocation needleGunGUI = new ResourceLocation(MFRProps.GUI_FOLDER + "needlegun.png");
    private final String name;

    public GuiNeedlegun(Container container, @Nonnull ItemStack item) {
        super(container);
        name = item.getDisplayName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(name, 8, 6, 0xEFEFEF);
        fontRenderer.drawString(I18n.translateToLocal("container.inventory"), 8, ySize - 96 + 4, 0x404040);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float gameTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(needleGunGUI);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
