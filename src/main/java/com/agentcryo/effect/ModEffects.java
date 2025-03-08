package com.agentcryo.effect;

import com.agentcryo.Testmod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {

    public static final RegistryEntry<StatusEffect> SLIMY = registerStatusEffect("slimy",
            new SlimyEffect(StatusEffectCategory.NEUTRAL, 0x36ebab)
                .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                    Identifier.of(Testmod.MOD_ID, "slimy"),
                    -0.25f,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(Testmod.MOD_ID, name), statusEffect);
    }

    public static void registerEffects() {
        Testmod.LOGGER.info("Registering Mod Effects for " + Testmod.MOD_ID);
    }
}
