package com.agentcryo.block.custom.entity;

import com.agentcryo.Testmod;
import com.agentcryo.block.ModBlockEntityType;
import com.agentcryo.item.ModItems;
import com.agentcryo.network.BlockPosPayload;
import com.agentcryo.recipe.custom.ForgeRecipe;
import com.agentcryo.screenhandler.ForgeScreenHandler;
import com.agentcryo.util.TickableBlockEntity;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.text.CompactNumberFormat;
import java.util.List;
import java.util.Optional;

public class ForgeEntity extends BlockEntity implements TickableBlockEntity, ExtendedScreenHandlerFactory<BlockPosPayload> {

    public static final Text TITLE = Text.translatable("container." + Testmod.MOD_ID + ".forge_screen_title");

    private int progress = 0;
    private final int maxProgress = 72;

    public ForgeEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.FORGE_BLOCK_ENTITY, pos, state);
    }

    private final SimpleInventory inventory = new SimpleInventory(12 ) {
        @Override
        public void markDirty() {
            super.markDirty();
            update();
        }
    };

    public SimpleInventory getInventory() {
        return this.inventory;
    }

    @Override
    public BlockPosPayload getScreenOpeningData(ServerPlayerEntity player) {
        return new BlockPosPayload(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return TITLE;
    }

    private void update() {
        markDirty();
        if(world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        var modID_DATA = new NbtCompound();
        modID_DATA.putInt("progress", this.progress);
        nbt.put(Testmod.MOD_ID, modID_DATA);
        Inventories.writeNbt(nbt, this.inventory.getHeldStacks(), registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        if(nbt.contains(Testmod.MOD_ID, NbtElement.COMPOUND_TYPE)) {
            var modID_DATA = nbt.getCompound(Testmod.MOD_ID);
            this.progress = modID_DATA.contains("progress", NbtElement.INT_TYPE) ? modID_DATA.getInt("progress") : 0;
            Inventories.readNbt(nbt, this.inventory.getHeldStacks(), registryLookup);
        }
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ForgeScreenHandler(syncId, playerInventory, this);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        var nbt = super.toInitialChunkDataNbt(registryLookup);
        writeNbt(nbt, registryLookup);
        return nbt;
    }

    public CraftingRecipeInput getRecipeInput() {
        return CraftingRecipeInput.create(3, 3, inventory.getHeldStacks().subList(0, 9));
    }

    public void tick() {
        if (world == null || world.isClient) return;

        int[] outputSlots = {9, 10, 11};

        if (areAllOutputSlotsReceivable(outputSlots) && this.hasRecipe()) {
            this.increaseCraftProgress();
            update();

            if (hasCraftingFinished()) {
                this.craftItem();
                this.resetProgress();
            }
        } else {
            this.resetProgress();
            update();
        }
    }

    private boolean areAllOutputSlotsReceivable(int[] outputSlots) {
        for (int slot : outputSlots) {
            if (!isOutputSlotsEmptyOrReceivable(slot)) return false;
        }
        return true;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void craftItem() {
        Optional<RecipeEntry<ForgeRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        List<ItemStack> outputs = recipe.get().value().getOutput();
        int[] outputSlots = {9, 10, 11}; // Define the output slots

        // Remove input items
        for (int craftingSlotIndex = 0; craftingSlotIndex < 9; craftingSlotIndex++) {
            this.getInventory().removeStack(craftingSlotIndex, 1);
        }

        // Distribute crafted items across available slots
        for (ItemStack output : outputs) {
            distributeOutput(output, outputSlots);
        }
    }

    private void distributeOutput(ItemStack output, int[] outputSlots) {
        for (int slot : outputSlots) {
            ItemStack existingStack = this.getInventory().getStack(slot);

            // If the slot contains the same item and has space, add to it
            if (ItemStack.areItemsEqual(existingStack, output) && existingStack.getCount() < existingStack.getMaxCount()) {
                int spaceAvailable = existingStack.getMaxCount() - existingStack.getCount();
                int amountToAdd = Math.min(output.getCount(), spaceAvailable);

                existingStack.increment(amountToAdd);
                return;
            }

            // If the slot is empty, place the item
            if (existingStack.isEmpty()) {
                this.getInventory().setStack(slot, output.copy());
                return; // Stop once item is added
            }
        }
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<RecipeEntry<ForgeRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        List<ItemStack> outputs = recipe.get().value().getOutput();
        int[] outputSlots = {9, 10, 11};

        return canInsertAllOutputs(outputs, outputSlots);
    }

    private Optional<RecipeEntry<ForgeRecipe>> getCurrentRecipe() {
        return getWorld().getRecipeManager().getFirstMatch(ForgeRecipe.Type.INSTANCE, getRecipeInput(), getWorld());
    }

    private boolean canInsertAllOutputs(List<ItemStack> outputs, int[] outputSlots) {
        for (ItemStack output : outputs) {
            boolean inserted = false;
            for (int slot : outputSlots) {
                if (canInsertAmountIntoOutputSlot(output, slot) && canInsertItemIntoOutputSlot(output.getItem(), slot)) {
                    inserted = true;
                    break;
                }
            }
            if (!inserted) return false; // If even one output item cannot fit, return false
        }
        return true;
    }

    private boolean canInsertItemIntoOutputSlot(Item result, int outputSlot) {
        return this.getInventory().getStack(outputSlot).getItem() == result || this.getInventory().getStack(outputSlot).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result, int outputSlot) {
        if (this.getInventory().getStack(outputSlot).isEmpty()) return true;
        return this.getInventory().getStack(outputSlot).getCount() + result.getCount() <= this.getInventory().getStack(outputSlot).getMaxCount();
    }

    private boolean isOutputSlotsEmptyOrReceivable(int outputSlot) {
        ItemStack stack = this.getInventory().getStack(outputSlot);
        return stack.isEmpty() || stack.getCount() < stack.getMaxCount();
    }
}
