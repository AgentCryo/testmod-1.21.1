package com.agentcryo.world.gen;

public class ModWorldGeneration {
    public static void generateModWorldGem() {
        ModOreGeneration.generateOres();
        ModTreeGeneration.generateTrees();
        ModBushGeneration.generateBushes();
    }
}
