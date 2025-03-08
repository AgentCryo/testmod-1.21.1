package com.agentcryo.recipe;

import com.agentcryo.Testmod;
import com.agentcryo.recipe.custom.ForgeRecipe;
import com.agentcryo.recipe.custom.RefineryRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes{
    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(Testmod.MOD_ID, ForgeRecipe.Serializer.ID),
                ForgeRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(Testmod.MOD_ID, ForgeRecipe.Type.ID),
                ForgeRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(Testmod.MOD_ID, RefineryRecipe.Serializer.ID),
                RefineryRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(Testmod.MOD_ID, RefineryRecipe.Type.ID),
                RefineryRecipe.Type.INSTANCE);
    }
}
