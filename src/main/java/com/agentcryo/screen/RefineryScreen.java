package com.agentcryo.screen;

import com.agentcryo.Testmod;
import com.agentcryo.screenhandler.ForgeScreenHandler;
import com.agentcryo.screenhandler.RefineryScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RefineryScreen extends HandledScreen<RefineryScreenHandler> {

    public static final Identifier TEXTURE = Identifier.of(Testmod.MOD_ID, "textures/gui/container/refinery_screen.png");

    public RefineryScreen(RefineryScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 184;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        renderProgress(context);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private void renderProgress(DrawContext context) {
        if(handler.isCrafting()) {
            context.drawTexture(TEXTURE, this.x + 83, this.y + 39, 179, 0, 10, handler.getScaledProgress());
        }
    }
}
