package com.agentcryo.util;

import com.agentcryo.Testmod;
import com.agentcryo.component.ModDataComponentTypes;
import com.agentcryo.item.ModItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ModModelPredicates {
    public static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(ModItems.CHISEL, Identifier.of(Testmod.MOD_ID, "used"),
                (stack, world, entity, seed) -> stack.get(ModDataComponentTypes.COORDS) != null ? 1f : 0f);
    }
}
