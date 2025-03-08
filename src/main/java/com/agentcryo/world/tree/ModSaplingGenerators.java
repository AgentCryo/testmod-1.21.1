package com.agentcryo.world.tree;

import com.agentcryo.Testmod;
import com.agentcryo.world.ModConfiguredFeatures;
import net.minecraft.block.SaplingGenerator;

import java.util.Optional;

public class ModSaplingGenerators {
    public static final SaplingGenerator GULM_WOOD = new SaplingGenerator(Testmod.MOD_ID + ":gulm_wood",
            Optional.empty(), Optional.of(ModConfiguredFeatures.GULM_WOOD_KEY), Optional.empty());
}
