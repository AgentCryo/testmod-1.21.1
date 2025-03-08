package com.agentcryo.screen;

import com.agentcryo.Testmod;
import com.agentcryo.screenhandler.DrillScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class DrillScreen extends HandledScreen<DrillScreenHandler> {

    public static final Identifier TEXTURE = Identifier.of(Testmod.MOD_ID, "textures/gui/container/drill_screen.png");

    public DrillScreen(DrillScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 184;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        float blocksBrokenPercentage = (float) this.handler.getBlocksBroken() / 320;
        int blocksBrokenValueBar = MathHelper.ceil(blocksBrokenPercentage * 66);
        context.fill(this.x + 144, this.y + 10 + 66 - blocksBrokenValueBar, this.x + 144 + 20, this.y + 10 + 66, 0xFFD4AF37);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);

        float blocksBrokenPercentage = (float) this.handler.getBlocksBroken() / 320;
        int blocksBrokenValueBar = MathHelper.ceil(blocksBrokenPercentage * 66);
        if (isPointWithinBounds(144, 10 + 66 - blocksBrokenValueBar, 20, blocksBrokenValueBar, mouseX, mouseY)) {
            context.drawTooltip(this.textRenderer, Text.literal(String.valueOf(this.handler.getBlocksBroken())), mouseX, mouseY);
        }
    }
}
