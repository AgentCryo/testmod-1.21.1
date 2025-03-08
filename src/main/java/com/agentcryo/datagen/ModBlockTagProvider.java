package com.agentcryo.datagen;

import com.agentcryo.block.ModBlocks;
import com.agentcryo.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.SHINY_BLOCK)
                .add(ModBlocks.MAGIC_BLOCK)
                .add(ModBlocks.SHINY_ORE)
                .add(ModBlocks.SHINY_DEEPSLATE_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.SHINY_BLOCK)
                .add(ModBlocks.MAGIC_BLOCK)
                .add(ModBlocks.SHINY_ORE)
                .add(ModBlocks.SHINY_DEEPSLATE_ORE);;

        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.GULM_WOOD_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES)
                .add(ModBlocks.GULM_WOOD_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WALLS)
                .add(ModBlocks.GULM_WOOD_WALL);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_STAR_FORGED_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.GULM_WOOD_LOG)
                .add(ModBlocks.STRIPPED_GULM_WOOD_LOG)
                .add(ModBlocks.GULM_WOOD_WOOD)
                .add(ModBlocks.STRIPPED_GULM_WOOD_WOOD);
    }
}
