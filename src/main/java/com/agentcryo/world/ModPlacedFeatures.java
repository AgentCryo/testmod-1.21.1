package com.agentcryo.world;

import com.agentcryo.Testmod;
import com.agentcryo.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> SHINY_ORE_PLACED_KEY = registerKey("shiny_ore_placed");
    public static final RegistryKey<PlacedFeature> GULM_WOOD_PLACED_KEY = registerKey("gulm_wood_placed");
    public static final RegistryKey<PlacedFeature> CLOUD_BERRY_PLACED_KEY = registerKey("cloud_berry_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, SHINY_ORE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.SHINY_ORE_KEY),
                    ModOrePlacement.modifiersWithCount(8,
                            HeightRangePlacementModifier.trapezoid(YOffset.fixed(-3), YOffset.fixed(3))
                    )
        );

        register(context, GULM_WOOD_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.GULM_WOOD_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                        PlacedFeatures.createCountExtraModifier(2, 0.1f, 2), ModBlocks.GULM_WOOD_SAPLING
                ));

        register(context, CLOUD_BERRY_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CLOUD_BERRY_BUSH),
                RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Testmod.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
