package com.agentcryo.item;

import com.agentcryo.Testmod;
import com.agentcryo.block.ModBlocks;
import com.agentcryo.enchantment.ModEnchantments;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup MOD_TESTING_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Testmod.MOD_ID, "mod_testing_group"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.SHINY))
                    .displayName(Text.translatable("itemgroup.testmod.mod_testing_group"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.SHINY_BLOCK);
                        entries.add(ModBlocks.MAGIC_BLOCK);

                        entries.add(ModBlocks.SHINY_ORE);
                        entries.add(ModBlocks.SHINY_DEEPSLATE_ORE);

                        entries.add(ModBlocks.SHINY_LAMP);
                        entries.add(ModBlocks.COUCH);
                        entries.add(ModBlocks.DRILL_BLOCK);
                        entries.add(ModBlocks.FORGE_BLOCK);
                        entries.add(ModBlocks.REFINERY_BLOCK);
                        entries.add(ModBlocks.FRAME);

                        entries.add(ModBlocks.GULM_WOOD_LOG);
                        entries.add(ModBlocks.GULM_WOOD_WOOD);
                        entries.add(ModBlocks.STRIPPED_GULM_WOOD_LOG);
                        entries.add(ModBlocks.STRIPPED_GULM_WOOD_WOOD);
                        entries.add(ModBlocks.GULM_WOOD_LEAVES);
                        entries.add(ModBlocks.GULM_WOOD_SAPLING);
                        entries.add(ModBlocks.GULM_WOOD_PLANKS);
                        entries.add(ModBlocks.GULM_WOOD_STAIRS);
                        entries.add(ModBlocks.GULM_WOOD_SLAB);
                        entries.add(ModBlocks.GULM_WOOD_BUTTON);
                        entries.add(ModBlocks.GULM_WOOD_PRESSURE_PLATE);
                        entries.add(ModBlocks.GULM_WOOD_FENCE);
                        entries.add(ModBlocks.GULM_WOOD_FENCE_GATE);
                        entries.add(ModBlocks.GULM_WOOD_WALL);
                        entries.add(ModBlocks.GULM_WOOD_DOOR);
                        entries.add(ModBlocks.GULM_WOOD_TRAP_DOOR);

                        entries.add(ModItems.SHINY);
                        entries.add(ModItems.UNREFINED_SHINY);

                        entries.add(ModItems.STAR_DUST);
                        entries.add(ModItems.STAR_FORGED_INGOT);

                        entries.add(ModItems.CHISEL);
                        entries.add(ModItems.HAMMER);

                        entries.add(ModItems.STAR_FORGED_SWORD);
                        entries.add(ModItems.STAR_FORGED_PICKAXE);
                        entries.add(ModItems.STAR_FORGED_AXE);
                        entries.add(ModItems.STAR_FORGED_SHOVEL);
                        entries.add(ModItems.STAR_FORGED_HOE);

                        entries.add(ModItems.STAR_FORGED_HELMET);
                        entries.add(ModItems.STAR_FORGED_CHESTPLATE);
                        entries.add(ModItems.STAR_FORGED_LEGGINGS);
                        entries.add(ModItems.STAR_FORGED_BOOTS);

                        entries.add(ModItems.SPACE_ICECREAM);

                        entries.add(ModItems.AMARANTH);
                        entries.add(ModItems.AMARANTH_GRAIN);

                        entries.add(ModItems.CLOUD_BERRIES);
                        entries.add(ModItems.CLOUD_BERRY_JAM);
                    })
                    .build());

    public static void registerItemGroups() {
        Testmod.LOGGER.info("Registering Mod Item Groups for " + Testmod.MOD_ID);
    }
}
