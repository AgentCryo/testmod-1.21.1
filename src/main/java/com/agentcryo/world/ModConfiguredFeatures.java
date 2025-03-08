package com.agentcryo.world;

import com.agentcryo.Testmod;
import com.agentcryo.block.ModBlocks;
import com.agentcryo.block.custom.CloudBerryBushBlock;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {
    // Configured Feature to Placed Feature to World Generation / Biome Modification

    public static final RegistryKey<ConfiguredFeature<?, ?>> SHINY_ORE_KEY = registerKey("shiny_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GULM_WOOD_KEY =registerKey("gulm_wood");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CLOUD_BERRY_BUSH =registerKey("cloud_berry_bush");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreFeatureConfig.Target> overworldShinyOres =
            List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.SHINY_ORE.getDefaultState()),
            OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.SHINY_DEEPSLATE_ORE.getDefaultState()));

        register(context, SHINY_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldShinyOres, 3));

        register(context, GULM_WOOD_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.GULM_WOOD_LOG),
                new StraightTrunkPlacer(6, 3, 2),

                BlockStateProvider.of(ModBlocks.GULM_WOOD_LEAVES),
                new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(3), 6),

                new TwoLayersFeatureSize(1, 0, 2)).build());

        register(context, CLOUD_BERRY_BUSH, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.CLOUD_BERRY_BUSH
                        .getDefaultState().with(CloudBerryBushBlock.AGE, 3))),
                List.of(Blocks.GRASS_BLOCK)));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Testmod.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
