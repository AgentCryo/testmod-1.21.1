package com.agentcryo.block.custom.entity;

import com.agentcryo.Testmod;
import com.agentcryo.block.ModBlockEntityType;
import com.agentcryo.network.BlockPosPayload;
import com.agentcryo.recipe.custom.RefineryRecipe;
import com.agentcryo.screenhandler.RefineryScreenHandler;
import com.agentcryo.util.TickableBlockEntity;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RefineryEntity extends BlockEntity implements TickableBlockEntity, ExtendedScreenHandlerFactory<BlockPosPayload> {

    private int progress = 0;
    private final int maxProgress = 144;

    public static final Text TITLE = Text.translatable("container." + Testmod.MOD_ID + ".refinery_screen_title");

    public RefineryEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.REFINERY_BLOCK_ENTITY, pos, state);
    }

    private final SimpleInventory inventory = new SimpleInventory(2 ) {
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
        return new RefineryScreenHandler(syncId, playerInventory, this);
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

    public void tick() {
        if (world == null || world.isClient) return;

        if (isOutputSlotsEmptyOrReceivable(1) && this.hasRecipe()) {
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
        Optional<RecipeEntry<RefineryRecipe>> recipe = getCurrentRecipe();
        if (recipe.isPresent()) {
            this.getInventory().removeStack(0, 1);
            ItemStack resultStack = recipe.get().value().getResult(null);
            this.getInventory().setStack(1, new ItemStack(
                    resultStack.getItem(),
                    this.getInventory().getStack(1).getCount() + resultStack.getCount()
            ));
        }
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    public CraftingRecipeInput getRecipeInput() {
        return CraftingRecipeInput.create(1, 1, inventory.getHeldStacks().subList(0, 1));
    }

    private boolean hasRecipe() {
        Optional<RecipeEntry<RefineryRecipe>> recipe = getCurrentRecipe();
        return recipe.isPresent() && canInsertAmountIntoOutputSlot(recipe.get().value().getResult(null), 1)
                && canInsertItemIntoOutputSlot(recipe.get().value().getResult(null).getItem(), 1);
    }

    private Optional<RecipeEntry<RefineryRecipe>> getCurrentRecipe() {
        return getWorld().getRecipeManager().getFirstMatch(RefineryRecipe.Type.INSTANCE, getRecipeInput(), getWorld());
    }

    private boolean canInsertItemIntoOutputSlot(Item result, int outputSlot) {
        return this.getInventory().getStack(outputSlot).getItem() == result || this.getInventory().getStack(outputSlot).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result, int outputSlot) {
        if(this.getInventory().getStack(outputSlot).isEmpty()) return true;
        return this.getInventory().getStack(outputSlot).getCount() + result.getCount() <= this.getInventory().getStack(outputSlot).getMaxCount();
    }

    private boolean isOutputSlotsEmptyOrReceivable(int outputSlot) {
        if(this.getInventory().getStack(outputSlot).isEmpty()) return true;
        if(this.getInventory().getStack(outputSlot).getCount() < this.getInventory().getStack(outputSlot).getMaxCount()) return true;
        return false;
    }
}
