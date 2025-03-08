package com.agentcryo.enchantment;

import com.agentcryo.Testmod;
import com.agentcryo.enchantment.custom.LightingStrikerEnchantmentEffect;
import com.agentcryo.enchantment.custom.PoisonTippedBladeEnchantmentEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantmentEffects {

    public static final MapCodec<? extends EnchantmentEntityEffect> LIGHTNING_STRIKER =
            registerEntityEffect("lightning_striker", LightingStrikerEnchantmentEffect.CODEC);

    public static final MapCodec<? extends EnchantmentEntityEffect> POISON_TIPPED_BLADE =
            registerEntityEffect("poison_tipped_blade", PoisonTippedBladeEnchantmentEffect.CODEC);

    public static MapCodec<? extends EnchantmentEntityEffect> registerEntityEffect(String name, MapCodec<? extends EnchantmentEntityEffect> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(Testmod.MOD_ID, name), codec);
    }

    public static void registerEnchantmentEffects() {
        Testmod.LOGGER.info("Registering Mod Enchantments Effects for " + Testmod.MOD_ID);
    }
}
