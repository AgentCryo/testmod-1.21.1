package com.agentcryo.potion;

import com.agentcryo.Testmod;
import com.agentcryo.effect.ModEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModPotions {

    public static final RegistryEntry<Potion> SLIMY_POTION = registerPotion("slimy_potion", new Potion(
            new StatusEffectInstance(ModEffects.SLIMY, 1200, 0)));

    public static RegistryEntry<Potion> registerPotion(String name, Potion potion) {
        return Registry.registerReference(Registries.POTION, Identifier.of(Testmod.MOD_ID, name), potion);
    }

    public static void registerPotions() {
        Testmod.LOGGER.info("Registering Mod Potions for " + Testmod.MOD_ID);
    }
}
