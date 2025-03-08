package com.agentcryo.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class ModFoodComponents {
    public static final FoodComponent SPACE_ICECREAM =
            new FoodComponent.Builder()
                .snack()
                .nutrition(2)
                .saturationModifier(0.25f)
                .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1200), 0.45f)
            .build();

    public static final FoodComponent CLOUD_BERRY =
            new FoodComponent.Builder()
                .snack()
                .nutrition(3)
                .saturationModifier(0.4f)
            .build();

    public static final FoodComponent CLOUD_BERRY_JAM =
            new FoodComponent.Builder()
                    .snack()
                    .nutrition(3)
                    .saturationModifier(0.4f)
                    .build();
}
