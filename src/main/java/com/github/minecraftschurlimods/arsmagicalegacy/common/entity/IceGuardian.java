package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class IceGuardian extends AbstractBoss {
    private boolean hasRightArm = true;
    private boolean hasLeftArm = true;
    private float orbitRotation;

    public IceGuardian(EntityType<? extends IceGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.RED);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 290D).add(Attributes.ARMOR, 23);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.ICE_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.ICE_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource == DamageSource.FREEZE) {
            pAmount = 0;
        } else if (pSource.isFire() || pSource == DamageSource.ON_FIRE || pSource == DamageSource.IN_FIRE) {
            pAmount *= 2.0f;
        }
        return super.hurt(pSource, pAmount);
    }

    public void returnOneArm() {
        if (!hasLeftArm) {
            hasLeftArm = true;
        } else if (!hasRightArm) {
            hasRightArm = true;
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    public void launchOneArm() {
        if (hasLeftArm) {
            hasLeftArm = false;
        } else if (hasRightArm) {
            hasRightArm = false;
        }
    }

    public boolean hasLeftArm() {
        return hasLeftArm;
    }

    public boolean hasRightArm() {
        return hasRightArm;
    }
}
