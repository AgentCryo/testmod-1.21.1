package com.agentcryo.datagen;

import com.agentcryo.block.ModBlocks;
import com.agentcryo.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.entity.feature.IronGolemCrackFeatureRenderer;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
            offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.SHINY, RecipeCategory.DECORATIONS, ModBlocks.SHINY_BLOCK);

            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CHISEL)
                    .pattern("   ")
                    .pattern(" E ")
                    .pattern(" S ")
                    .input('E', Items.EMERALD)
                    .input('S', Items.STICK)
                    .criterion(hasItem(Items.EMERALD), conditionsFromItem(ModItems.CHISEL))
                    .offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.SHINY_LAMP)
                    .pattern("SSS")
                    .pattern("SDS")
                    .pattern("SSS")
                    .input('S', ModItems.SHINY)
                    .input('D', ModItems.STAR_DUST)
                    .criterion(hasItem(ModItems.STAR_DUST), conditionsFromItem(ModBlocks.SHINY_LAMP))
                    .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.FRAME)
                .pattern("III")
                .pattern("I I")
                .pattern("III")
                .input('I', Items.IRON_INGOT)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(ModBlocks.FRAME))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.FORGE_BLOCK)
                .pattern("ISI")
                .pattern("SFS")
                .pattern("IBI")
                .input('F', ModBlocks.FRAME)
                .input('B', Blocks.BLAST_FURNACE)
                .input('S', Blocks.SMOOTH_STONE)
                .input('I', Items.IRON_INGOT)
                .criterion(hasItem(ModBlocks.FRAME), conditionsFromItem(ModBlocks.FORGE_BLOCK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.REFINERY_BLOCK)
                .pattern("ISI")
                .pattern("SFS")
                .pattern("IGI")
                .input('F', ModBlocks.FRAME)
                .input('G', Blocks.GRINDSTONE)
                .input('S', Blocks.SMOOTH_STONE)
                .input('I', Items.IRON_INGOT)
                .criterion(hasItem(ModBlocks.FRAME), conditionsFromItem(ModBlocks.REFINERY_BLOCK))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.GULM_WOOD_BUTTON, 1)
                     .input(ModBlocks.GULM_WOOD_PLANKS)
                     .criterion(hasItem(ModBlocks.GULM_WOOD_PLANKS), conditionsFromItem(ModBlocks.GULM_WOOD_BUTTON))
                     .offerTo(exporter);

            offerBarkBlockRecipe(exporter, ModBlocks.GULM_WOOD_WOOD, ModBlocks.GULM_WOOD_LOG);
            offerBarkBlockRecipe(exporter, ModBlocks.STRIPPED_GULM_WOOD_WOOD, ModBlocks.STRIPPED_GULM_WOOD_LOG);

            createDoorRecipe(ModBlocks.GULM_WOOD_DOOR, Ingredient.ofItems(ModBlocks.GULM_WOOD_PLANKS))
                    .criterion(hasItem(ModBlocks.GULM_WOOD_PLANKS), conditionsFromItem(ModBlocks.GULM_WOOD_DOOR))
                    .offerTo(exporter);
            createTrapdoorRecipe(ModBlocks.GULM_WOOD_TRAP_DOOR, Ingredient.ofItems(ModBlocks.GULM_WOOD_PLANKS))
                    .criterion(hasItem(ModBlocks.GULM_WOOD_PLANKS), conditionsFromItem(ModBlocks.GULM_WOOD_TRAP_DOOR))
                    .offerTo(exporter);
            createStairsRecipe(ModBlocks.GULM_WOOD_STAIRS, Ingredient.ofItems(ModBlocks.GULM_WOOD_PLANKS))
                    .criterion(hasItem(ModBlocks.GULM_WOOD_PLANKS), conditionsFromItem(ModBlocks.GULM_WOOD_STAIRS))
                    .offerTo(exporter);
            createPressurePlateRecipe(RecipeCategory.REDSTONE, ModBlocks.GULM_WOOD_PRESSURE_PLATE, Ingredient.ofItems(ModBlocks.GULM_WOOD_PLANKS))
                    .criterion(hasItem(ModBlocks.GULM_WOOD_PLANKS), conditionsFromItem(ModBlocks.GULM_WOOD_PRESSURE_PLATE))
                    .offerTo(exporter);
            createFenceRecipe(ModBlocks.GULM_WOOD_FENCE, Ingredient.ofItems(ModBlocks.GULM_WOOD_PLANKS))
                    .criterion(hasItem(ModBlocks.GULM_WOOD_PLANKS), conditionsFromItem(ModBlocks.GULM_WOOD_FENCE))
                    .offerTo(exporter);
            createFenceGateRecipe(ModBlocks.GULM_WOOD_FENCE_GATE, Ingredient.ofItems(ModBlocks.GULM_WOOD_PLANKS))
                    .criterion(hasItem(ModBlocks.GULM_WOOD_PLANKS), conditionsFromItem(ModBlocks.GULM_WOOD_FENCE_GATE))
                    .offerTo(exporter);
            offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GULM_WOOD_WALL, ModBlocks.GULM_WOOD_PLANKS);
        }
    }
