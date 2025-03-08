package com.agentcryo.datagen;

import com.agentcryo.block.ModBlocks;
import com.agentcryo.item.ModItems;
import com.agentcryo.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider  extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Items.MAGIC_BLOCK_TO_DIAMOND)
                .add(ModItems.SHINY)
                .add(Blocks.COAL_BLOCK.asItem());

        getOrCreateTagBuilder(ModTags.Items.STAR_FORGED_REPAIR )
                .add(ModItems.STAR_DUST.asItem());

        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModItems.STAR_FORGED_SWORD);
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.STAR_FORGED_PICKAXE)
                .add(ModItems.HAMMER);
        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModItems.STAR_FORGED_AXE);
        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ModItems.STAR_FORGED_SHOVEL);
        getOrCreateTagBuilder(ItemTags.HOES)
                .add(ModItems.STAR_FORGED_HOE);

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.STAR_FORGED_HELMET)
                .add(ModItems.STAR_FORGED_CHESTPLATE)
                .add(ModItems.STAR_FORGED_LEGGINGS)
                .add(ModItems.STAR_FORGED_BOOTS);

        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.GULM_WOOD_LOG.asItem())
                .add(ModBlocks.STRIPPED_GULM_WOOD_LOG.asItem())
                .add(ModBlocks.GULM_WOOD_WOOD.asItem())
                .add(ModBlocks.STRIPPED_GULM_WOOD_WOOD.asItem());

        getOrCreateTagBuilder(ItemTags.PLANKS)
                .add(ModBlocks.GULM_WOOD_PLANKS.asItem());
    }
}
