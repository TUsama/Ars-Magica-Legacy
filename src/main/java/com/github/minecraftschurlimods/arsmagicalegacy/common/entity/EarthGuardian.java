package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;

public class EarthGuardian extends AbstractBoss {
    public boolean leftArm = false;
    private float rodRotation = 0;

    public EarthGuardian(EntityType<? extends EarthGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.BLUE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 140).add(Attributes.ARMOR, 23);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.EARTH_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.EARTH_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.EARTH_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.EARTH_GUARDIAN_ATTACK.get();
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource == DamageSource.FREEZE) {
            pAmount *= 2.0f;
        } else if (pSource == DamageSource.LIGHTNING_BOLT) {
            pAmount /= 4.0f;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public int getMaxFallDistance() {
        return getTarget() == null ? 3 : 3 + (int) (getHealth() - 1.0F);
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return pLevel.getFluidState(pPos).is(FluidTags.WATER) ? 10.0F + pLevel.getBrightness(pPos) - 0.5F : super.getWalkTargetValue(pPos, pLevel);
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (isEffectiveAi() && isInWater()) {
            moveRelative(0.1F, pTravelVector);
            move(MoverType.SELF, getDeltaMovement());
            setDeltaMovement(getDeltaMovement().scale(0.9D));
        } else {
            super.travel(pTravelVector);
        }
    }
}
