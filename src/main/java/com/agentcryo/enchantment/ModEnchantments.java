package com.agentcryo.enchantment;

import com.agentcryo.Testmod;
import com.agentcryo.enchantment.custom.LightingStrikerEnchantmentEffect;
import com.agentcryo.enchantment.custom.PoisonTippedBladeEnchantmentEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class ModEnchantments {

    public static final RegistryKey<Enchantment> LIGHTING_STRIKER = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Testmod.MOD_ID,"lightning_striker"));
    public static final RegistryKey<Enchantment> POISON_TIPPED_BLADE = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Testmod.MOD_ID,"poison_tipped_blade"));

    public static void bootstrap(Registerable<Enchantment> registerable) {
        var enchantments = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        var items = registerable.getRegistryLookup(RegistryKeys.ITEM);

        register(registerable, LIGHTING_STRIKER, Enchantment.builder(Enchantment.definition(
            items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                3, 5,
                Enchantment.leveledCost(5, 7),
                Enchantment.leveledCost(25, 5),
                4,
                AttributeModifierSlot.MAINHAND))
                .exclusiveSet(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE_SET))
                .addEffect(EnchantmentEffectComponentTypes.POST_ATTACK,
                        EnchantmentEffectTarget.ATTACKER, EnchantmentEffectTarget.VICTIM, new LightingStrikerEnchantmentEffect()));

        register(registerable, POISON_TIPPED_BLADE, Enchantment.builder(Enchantment.definition(
                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        3, 3,
                        Enchantment.leveledCost(10, 7),
                        Enchantment.leveledCost(30, 5),
                        3,
                        AttributeModifierSlot.MAINHAND))
                .exclusiveSet(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE_SET))
                .addEffect(EnchantmentEffectComponentTypes.POST_ATTACK,
                        EnchantmentEffectTarget.ATTACKER, EnchantmentEffectTarget.VICTIM, new PoisonTippedBladeEnchantmentEffect()));
    }

    private static void register(Registerable<Enchantment> registry, RegistryKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.getValue()));
    }
}
