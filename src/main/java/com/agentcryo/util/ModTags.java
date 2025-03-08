package com.agentcryo.util;

import com.agentcryo.Testmod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> NEEDS_STAR_FORGED_TOOL = createTag("needs_star_forged_tool");
        public static final TagKey<Block> INCORRECT_FOR_STAR_FORGED_TOOL = createTag("incorrect_for_star_forged_tool");

        public static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Testmod.MOD_ID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> MAGIC_BLOCK_TO_DIAMOND = createTag("magic_block_to_diamonds");
        public static final TagKey<Item> STAR_FORGED_REPAIR = createTag("star_forged_repair");

        public static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(Testmod.MOD_ID, name));
        }
    }
}
