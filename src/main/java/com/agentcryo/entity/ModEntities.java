package com.agentcryo.entity;

import com.agentcryo.Testmod;
import com.agentcryo.entity.custom.ChairEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<ChairEntity> COUCH = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Testmod.MOD_ID, "couch_entity"),
            EntityType.Builder.create(ChairEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5f, 0.3f).build());

    public static void registerModEntities() {
        Testmod.LOGGER.info("Registering Mod Entities for " + Testmod.MOD_ID);
    }
}

