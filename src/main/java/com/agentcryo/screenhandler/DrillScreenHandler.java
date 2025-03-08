package com.agentcryo.screenhandler;

import com.agentcryo.block.ModBlocks;
import com.agentcryo.block.custom.entity.DrillEntity;
import com.agentcryo.network.BlockPosPayload;
import com.agentcryo.screenhandler.type.ModScreenHandlerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

public class DrillScreenHandler extends ScreenHandler {

    private final DrillEntity blockEntity;
    private final ScreenHandlerContext context;

    //Client Side Constructor
    public DrillScreenHandler(int syncId, PlayerInventory playerInventory, BlockPosPayload blockPosPayload) {
        this(syncId, playerInventory, (DrillEntity) playerInventory.player.getWorld().getBlockEntity(blockPosPayload.pos()));
    }

    //Server Side Constructor / Main Constructor
    public DrillScreenHandler(int syncId, PlayerInventory playerInventory, DrillEntity block) {
        super(ModScreenHandlerType.DRILL, syncId);

        this.blockEntity = block;
        this.context = ScreenHandlerContext.create(this.blockEntity.getWorld(), this.blockEntity.getPos());

        SimpleInventory inventory = this.blockEntity.getInventory();
        checkSize(inventory, this.blockEntity.getInventory().size());
        inventory.onOpen(playerInventory.player);

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addBlockInventory(inventory);
    }
    private void addBlockInventory(SimpleInventory inventory) {
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 7; column++) {
                addSlot(new Slot(inventory, column + (row * 7), 8 + (column * 18), 18 + (row * 18)));
            }
        }
    }

    private void addPlayerInventory(PlayerInventory playerInv) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInv, 9 + (column + (row * 9)), 8 + (column * 18), 102 + (row * 18)));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInv) {
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInv, column, 8 + (column * 18), 160));
        }
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.blockEntity.getInventory().onClose(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.blockEntity.getInventory().size()) {
                if (!this.insertItem(originalStack, this.blockEntity.getInventory().size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.blockEntity.getInventory().size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModBlocks.DRILL_BLOCK);
    }

    public DrillEntity getBlockEntity() {
        return this.blockEntity;
    }

    public int getBlocksBroken() {
        return this.blockEntity.getBlocksBroken();
    }
}
