package com.agentcryo.block.custom.entity;

import com.agentcryo.Testmod;
import com.agentcryo.block.ModBlockEntityType;
import com.agentcryo.network.BlockPosPayload;
import com.agentcryo.screenhandler.DrillScreenHandler;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DrillEntity extends BlockEntity implements TickableBlockEntity, ExtendedScreenHandlerFactory<BlockPosPayload> {

    public static final Text TITLE = Text.translatable("container." + Testmod.MOD_ID + ".drill_screen_title");

    private final SimpleInventory inventory = new SimpleInventory(28 ) {
        @Override
        public void markDirty() {
            super.markDirty();
            update();
        }
    };

    private final InventoryStorage inventoryStorage = InventoryStorage.of(inventory, null);

    public InventoryStorage getInventoryStorage(Direction direction) {
        return inventoryStorage;
    }

    public SimpleInventory getInventory() {
        return this.inventory;
    }

    private int ticks = 0;
    private BlockPos miningPos = this.pos.down();
    private int blocksBroken = 0;

    public DrillEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.DRILL_BLOCK_ENTITY, pos, state);
    }

    public BlockPos getMiningPos() {
        return miningPos;
    }

    @Override
    public void tick() {
        if(this.world == null || this.world.isClient) return;

        if(this.ticks++ % 20 == 0) {
            if(this.miningPos.getY() <= this.world.getBottomY()) this.miningPos = this.pos.down();

            BlockState state = this.world.getBlockState(this.miningPos);
            if(state.isAir() && state.getHardness(this.world, this.miningPos) < 0) this.miningPos = this.miningPos.down();

            List<ItemStack> blockDrops = new ArrayList<>(state.getDroppedStacks(
                    new LootContextParameterSet.Builder((ServerWorld)this.world)
                            .add(LootContextParameters.TOOL, Items.DIAMOND_PICKAXE.getDefaultStack())
                            .add(LootContextParameters.ORIGIN, this.miningPos.toCenterPos())
                            .addOptional(LootContextParameters.BLOCK_ENTITY, this)
            ));

            boolean broken = this.world.breakBlock(this.miningPos, false);

            Storage<ItemVariant> storage = findItemStorage((ServerWorld) this.world, this.pos);
            if(storage != null && storage.supportsInsertion()) {
                insertDrops(blockDrops, storage);
            }

            if(!blockDrops.isEmpty()) {
                spawnDrops(blockDrops, (ServerWorld) this.world, this.pos);
            }

            this.miningPos = this.miningPos.down();
            if(broken) {
                blocksBroken++;
            }
            update();
        }
    }

    private void update() {
        markDirty();
        if(world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    private static Storage<ItemVariant> findItemStorage(ServerWorld world, BlockPos pos) {
        return ItemStorage.SIDED.find(world, pos, Direction.DOWN);
    }

    private static void insertDrops(List<ItemStack> blockDrops, Storage<ItemVariant> aboveStorage) {
        for (ItemStack blockDrop : blockDrops) {
            try(Transaction transaction = Transaction.openOuter()) {
                long inserted = aboveStorage.insert(ItemVariant.of(blockDrop), blockDrop.getCount(), transaction);
                if(inserted > 0) {
                    blockDrop.decrement((int) inserted);
                    transaction.commit();
                }
            }
        }

        blockDrops.removeIf(ItemStack::isEmpty);
    }

    private static void spawnDrops(List<ItemStack> blockDrops, ServerWorld world, BlockPos pos) {
        for (ItemStack blockDrop : blockDrops) {
            ItemScatterer.spawn(world, pos.getX() + 0.5d, pos.getY() + 1.0d, pos.getZ() + 1.0d, blockDrop);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        var modID_DATA = new NbtCompound();
        modID_DATA.putInt("ticks", this.ticks);
        modID_DATA.putLong("mining_pos", this.miningPos.asLong());
        modID_DATA.putInt("blocks_broken", this.blocksBroken);
        nbt.put(Testmod.MOD_ID, modID_DATA);
        Inventories.writeNbt(nbt, this.inventory.getHeldStacks(), registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        if(nbt.contains(Testmod.MOD_ID, NbtElement.COMPOUND_TYPE)) {
            var modID_DATA = nbt.getCompound(Testmod.MOD_ID);
            this.ticks = modID_DATA.contains("ticks", NbtElement.INT_TYPE) ? modID_DATA.getInt("ticks") : 0;
            this.miningPos = modID_DATA.contains("mining_pos", NbtElement.LONG_TYPE) ? BlockPos.fromLong(modID_DATA.getLong("mining_pos")) : BlockPos.fromLong(0);
            this.blocksBroken = modID_DATA.contains("blocks_broken", NbtElement.INT_TYPE) ? modID_DATA.getInt("blocks_broken") : 0;
            Inventories.readNbt(nbt, this.inventory.getHeldStacks(), registryLookup);
        }
    }

    @Override
    public BlockPosPayload getScreenOpeningData(ServerPlayerEntity player) {
        return new BlockPosPayload(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return TITLE;
    }

    public int getBlocksBroken() {
        return this.blocksBroken;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new DrillScreenHandler(syncId, playerInventory, this);
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
}
