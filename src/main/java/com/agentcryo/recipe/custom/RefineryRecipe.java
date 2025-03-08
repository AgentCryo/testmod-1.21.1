package com.agentcryo.recipe.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class RefineryRecipe implements CraftingRecipe {
    final Ingredient recipeItem;
    final ItemStack result;

    public RefineryRecipe(ItemStack result, Ingredient input) {
        this.result = result;
        this.recipeItem = input;
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        return recipeItem.test(input.getStackInSlot(0));
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return result;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return result;
    }

    public DefaultedList<Ingredient> getIngredients() {
        return DefaultedList.ofSize(1, recipeItem);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public CraftingRecipeCategory getCategory() {
        return CraftingRecipeCategory.MISC;
    }

    public static class Type implements RecipeType<RefineryRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "refinery";
    }

    public static class Serializer implements RecipeSerializer<RefineryRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "refinery";

        public static final MapCodec<RefineryRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            {
                return instance.group(
                    ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter((recipe) ->
                    {
                        return recipe.result;
                    }),
                        Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredients").forGetter((recipe)-> {
                        return recipe.recipeItem;
                    })
                ).apply(instance, RefineryRecipe::new);
            }
        );

        private static RefineryRecipe read(RegistryByteBuf buf) {
            Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
            ItemStack result = ItemStack.PACKET_CODEC.decode(buf);
            return new RefineryRecipe(result, ingredient);
        }

        private static void write(RegistryByteBuf buf, RefineryRecipe recipe) {
            Ingredient.PACKET_CODEC.encode(buf, recipe.recipeItem);
            ItemStack.PACKET_CODEC.encode(buf, recipe.result);
        }

        public static final PacketCodec<RegistryByteBuf, RefineryRecipe> PACKET_CODEC = PacketCodec.ofStatic(RefineryRecipe.Serializer::write, RefineryRecipe.Serializer::read);

        //public static final PacketCodec<RegistryByteBuf, ForgeRecipe> PACKET_CODEC = PacketCodec.ofStatic(
        //    ByteBuf
        //);

        @Override
        public MapCodec<RefineryRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, RefineryRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
