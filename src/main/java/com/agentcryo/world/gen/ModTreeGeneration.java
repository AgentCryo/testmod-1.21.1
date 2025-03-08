package com.agentcryo.world.gen;

import com.agentcryo.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class ModTreeGeneration {
    public static void generateTrees() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST)
        , GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.GULM_WOOD_PLACED_KEY);
    }
}
