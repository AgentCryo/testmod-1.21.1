package com.agentcryo.datagen;

import com.agentcryo.block.ModBlocks;
import com.agentcryo.block.custom.AmaranthCropBlock;
import com.agentcryo.block.custom.CloudBerryBushBlock;
import com.agentcryo.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {

    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);

        addDrop(ModBlocks.SHINY_BLOCK, multipleOreDrops(ModBlocks.SHINY_BLOCK, ModItems.UNREFINED_SHINY, 9,9));
        addDrop(ModBlocks.SHINY_ORE, multipleOreDrops(ModBlocks.SHINY_ORE, ModItems.UNREFINED_SHINY, 1,3));
        addDrop(ModBlocks.SHINY_DEEPSLATE_ORE, multipleOreDrops(ModBlocks.SHINY_DEEPSLATE_ORE, ModItems.UNREFINED_SHINY, 2,5));

        addDrop(ModBlocks.GULM_WOOD_LOG);
        addDrop(ModBlocks.GULM_WOOD_WOOD);
        addDrop(ModBlocks.STRIPPED_GULM_WOOD_LOG);
        addDrop(ModBlocks.STRIPPED_GULM_WOOD_WOOD);
        addDrop(ModBlocks.GULM_WOOD_SAPLING);
        addDrop(ModBlocks.GULM_WOOD_LEAVES, leavesDrops(ModBlocks.GULM_WOOD_LEAVES, ModBlocks.GULM_WOOD_SAPLING, 0.0625f));

        addDrop(ModBlocks.GULM_WOOD_PLANKS);
        addDrop(ModBlocks.GULM_WOOD_STAIRS);
        addDrop(ModBlocks.GULM_WOOD_SLAB, slabDrops(ModBlocks.GULM_WOOD_SLAB));

        addDrop(ModBlocks.GULM_WOOD_BUTTON);
        addDrop(ModBlocks.GULM_WOOD_PRESSURE_PLATE);

        addDrop(ModBlocks.GULM_WOOD_FENCE);
        addDrop(ModBlocks.GULM_WOOD_FENCE_GATE);
        addDrop(ModBlocks.GULM_WOOD_WALL);

        addDrop(ModBlocks.GULM_WOOD_DOOR, doorDrops(ModBlocks.GULM_WOOD_DOOR));
        addDrop(ModBlocks.GULM_WOOD_TRAP_DOOR);

        BlockStatePropertyLootCondition.Builder builder2 = BlockStatePropertyLootCondition.builder(ModBlocks.AMARANTH_CROP)
                .properties(StatePredicate.Builder.create().exactMatch(AmaranthCropBlock.AGE, AmaranthCropBlock.MAX_AGE));
        this.addDrop(ModBlocks.AMARANTH_CROP, this.cropDrops(ModBlocks.AMARANTH_CROP, ModItems.AMARANTH, ModItems.AMARANTH_GRAIN, builder2));

        this.addDrop(ModBlocks.CLOUD_BERRY_BUSH, (block) -> {
            return (LootTable.Builder)this.applyExplosionDecay(block, LootTable.builder().pool(LootPool.builder().conditionally(BlockStatePropertyLootCondition.builder(ModBlocks.CLOUD_BERRY_BUSH).properties(StatePredicate.Builder.create().exactMatch(CloudBerryBushBlock.AGE, 3))).with(ItemEntry.builder(ModItems.CLOUD_BERRIES)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 3.0F))).apply(ApplyBonusLootFunction.uniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))).pool(LootPool.builder().conditionally(BlockStatePropertyLootCondition.builder(ModBlocks.CLOUD_BERRY_BUSH).properties(StatePredicate.Builder.create().exactMatch(CloudBerryBushBlock.AGE, 2))).with(ItemEntry.builder(ModItems.CLOUD_BERRIES)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F))).apply(ApplyBonusLootFunction.uniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))));
        });
    }

    public LootTable.Builder multipleOreDrops(Block drop, Item item, float minDrops, float maxDrops) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(drop, this.applyExplosionDecay(drop, ((LeafEntry.Builder<?>)
                ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops))))
                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))));
    }
}
