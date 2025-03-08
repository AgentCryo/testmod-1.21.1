package com.agentcryo.block;

import com.agentcryo.Testmod;
import com.agentcryo.block.custom.entity.DrillEntity;
import com.agentcryo.block.custom.entity.ForgeEntity;
import com.agentcryo.block.custom.entity.RefineryEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntityType {
    public static final BlockEntityType<DrillEntity> DRILL_BLOCK_ENTITY = registerBlockEntity("drill_block_entity",
            BlockEntityType.Builder.create(
                    DrillEntity::new, ModBlocks.DRILL_BLOCK
            ).build());

    public static final BlockEntityType<ForgeEntity> FORGE_BLOCK_ENTITY = registerBlockEntity("forge_block_entity",
            BlockEntityType.Builder.create(
                    ForgeEntity::new, ModBlocks.FORGE_BLOCK
            ).build());

    public static final BlockEntityType<RefineryEntity> REFINERY_BLOCK_ENTITY = registerBlockEntity("refinery_block_entity",
            BlockEntityType.Builder.create(
                    RefineryEntity::new, ModBlocks.REFINERY_BLOCK
            ).build());

    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, BlockEntityType<T> type) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Testmod.MOD_ID, type);
    }

    public static void registerBlockEntities() {
        Testmod.LOGGER.info("Registering Mod Block Entities for " + Testmod.MOD_ID);
    }
}
