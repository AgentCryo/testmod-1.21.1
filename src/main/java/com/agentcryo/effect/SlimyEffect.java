package com.agentcryo.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.Vec3d;

public class SlimyEffect extends StatusEffect {

    protected SlimyEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {

        if(entity.horizontalCollision) {
            Vec3d initVec3 = entity.getVelocity();
            Vec3d climbVec3 = new Vec3d(initVec3.x, 0.2d, initVec3.z);
            entity.setVelocity(climbVec3.multiply(0.96d));
            return true;
        }

        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
