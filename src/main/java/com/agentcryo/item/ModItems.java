package com.agentcryo.item;

import com.agentcryo.Testmod;
import com.agentcryo.block.ModBlocks;
import com.agentcryo.item.custom.ChiselItem;
import com.agentcryo.item.custom.CloudBerryJam;
import com.agentcryo.item.custom.HammerItem;
import com.agentcryo.item.custom.ModArmorItem;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModItems {

    public static final Item SHINY = registerItem("shiny", new Item(
            new Item.Settings()));

    public static final Item UNREFINED_SHINY = registerItem("unrefined_shiny",
            new Item(new Item.Settings()));

    public static final Item STAR_DUST = registerItem("star_dust",
            new Item(new Item.Settings()
            ));
    public static final Item STAR_FORGED_INGOT = registerItem("star_forged_ingot",
            new Item(new Item.Settings()
            ));

    public static final Item SPACE_ICECREAM = registerItem("space_icecream", new Item(new Item.Settings().food(ModFoodComponents.SPACE_ICECREAM)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.testmod.space_icecream_tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item AMARANTH = registerItem("amaranth", new Item( new Item.Settings()));

    public static final Item AMARANTH_GRAIN = registerItem("amaranth_grain",
            new AliasedBlockItem(ModBlocks.AMARANTH_CROP, new Item.Settings()));

    public static final Item CLOUD_BERRIES = registerItem("cloud_berries", new AliasedBlockItem(ModBlocks.CLOUD_BERRY_BUSH, new Item.Settings().food(ModFoodComponents.CLOUD_BERRY)));
    public static final Item CLOUD_BERRY_JAM = registerItem("cloud_berry_jam", new CloudBerryJam(new Item.Settings().food(ModFoodComponents.CLOUD_BERRY_JAM)));

    public static final Item CHISEL = registerItem("chisel",
            new ChiselItem(new Item.Settings()
                    .maxDamage(32)));

    public static final Item HAMMER = registerItem("hammer",
            new HammerItem(ModToolMaterials.STAR_FORGED, new Item.Settings()
                    .attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.STAR_FORGED, 7, -3.4f))));

    public static final Item STAR_FORGED_SWORD = registerItem("star_forged_sword",
            new SwordItem(ModToolMaterials.STAR_FORGED ,new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.STAR_FORGED, 3, -2.4f))
            ));
    public static final Item STAR_FORGED_PICKAXE = registerItem("star_forged_pickaxe",
            new PickaxeItem(ModToolMaterials.STAR_FORGED ,new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.STAR_FORGED, 2, -2.0f))
            ));
    public static final Item STAR_FORGED_AXE = registerItem("star_forged_axe",
            new AxeItem(ModToolMaterials.STAR_FORGED ,new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.STAR_FORGED, 6, -3.2f))
            ));
    public static final Item STAR_FORGED_SHOVEL = registerItem("star_forged_shovel",
            new ShovelItem(ModToolMaterials.STAR_FORGED ,new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.STAR_FORGED, 1, -3f))
            ));
    public static final Item STAR_FORGED_HOE = registerItem("star_forged_hoe",
            new HoeItem(ModToolMaterials.STAR_FORGED ,new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.STAR_FORGED, 0, -3.0f))
            ));

    public static final Item STAR_FORGED_HELMET = registerItem("star_forged_helmet",
            new ModArmorItem(ModArmorMaterials.STAR_FORGED, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
    public static final Item STAR_FORGED_CHESTPLATE = registerItem("star_forged_chestplate",
            new ModArmorItem(ModArmorMaterials.STAR_FORGED, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(15))));
    public static final Item STAR_FORGED_LEGGINGS = registerItem("star_forged_leggings",
            new ModArmorItem(ModArmorMaterials.STAR_FORGED, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));
    public static final Item STAR_FORGED_BOOTS = registerItem("star_forged_boots",
            new ModArmorItem(ModArmorMaterials.STAR_FORGED, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15))));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Testmod.MOD_ID, name), item);
    }

    //public static Item registerItem(String id, Function<Item.Settings, Item> factory) {
    //    return register(keyOf(id), factory, new Item.Settings());
    //}

    public static void registerModItems() {
        Testmod.LOGGER.info("Registering Mod Items for " + Testmod.MOD_ID);

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
        //    entries.add(SHINY);
        //    entries.add(UNREFINED_SHINY);
        //});
    }
}
