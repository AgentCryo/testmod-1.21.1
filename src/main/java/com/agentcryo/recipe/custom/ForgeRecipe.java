package com.agentcryo.recipe.custom;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class ForgeRecipe implements Recipe<CraftingRecipeInput> {
    final RawShapedRecipe raw;
    final List<ItemStack> result;

    public ForgeRecipe(List<ItemStack> result,  RawShapedRecipe raw) {
        this.result = result;
        this.raw = raw;
    }

    public List<ItemStack> getOutput() {
        return result;
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        return raw.matches(input);
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return result.get(0);
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return result.get(0);
    }

    public DefaultedList<Ingredient> getIngredients() {
        return this.raw.getIngredients();
    }

    //RecipeSerializer<ForgeRecipe> FORGE_CRAFTING= register("forge_crafting", new ForgeRecipe.Serializer());

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ForgeRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "forge";
    }

    public static class Serializer implements RecipeSerializer<ForgeRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "forge";

        //From @The...Why...Even...How? on discord
        public static final Codec<List<ItemStack>> OUTPUT_CODEC = Codec.either(
                ItemStack.VALIDATED_CODEC,
                ItemStack.VALIDATED_CODEC.listOf()
        ).xmap(
                either -> either.map(List::of, list -> list), // Convert Either to List<ItemStack>
                list -> list.size() == 1
                        ? Either.left(list.get(0)) // If only one element, store as Left
                        : Either.right(list) // Otherwise, store as Right
        ).validate(list -> list.size() < 4 && !list.isEmpty() ? DataResult.success(list) : DataResult.error(() -> "Too many outputs"));

        public static final MapCodec<ForgeRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            {
                return instance.group(
                    OUTPUT_CODEC.fieldOf("result").forGetter((recipe) ->
                    {
                        return recipe.result;
                    }),
                    RawShapedRecipe.CODEC.forGetter((recipe) -> {
                        return recipe.raw;
                    })
                ).apply(instance, ForgeRecipe::new);
            }
        );

        private static ForgeRecipe read(RegistryByteBuf buf) {
            RawShapedRecipe rawShapedRecipe = RawShapedRecipe.PACKET_CODEC.decode(buf);
            List<ItemStack> result = ItemStack.PACKET_CODEC.collect(PacketCodecs.toList()).decode(buf);
            return new ForgeRecipe(result, rawShapedRecipe);
        }

        private static void write(RegistryByteBuf buf, ForgeRecipe recipe) {
            RawShapedRecipe.PACKET_CODEC.encode(buf, recipe.raw);
            ItemStack.PACKET_CODEC.collect(PacketCodecs.toList()).encode(buf, recipe.result);
        }

        public static final PacketCodec<RegistryByteBuf, ForgeRecipe> PACKET_CODEC = PacketCodec.ofStatic(ForgeRecipe.Serializer::write, ForgeRecipe.Serializer::read);

        //public static final PacketCodec<RegistryByteBuf, ForgeRecipe> PACKET_CODEC = PacketCodec.ofStatic(
        //    ByteBuf
        //);

        @Override
        public MapCodec<ForgeRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, ForgeRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
