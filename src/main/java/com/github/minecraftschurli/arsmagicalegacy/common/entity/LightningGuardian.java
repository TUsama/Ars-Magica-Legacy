package com.github.minecraftschurli.arsmagicalegacy.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class LightningGuardian extends Monster {
    public LightningGuardian(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new RandomLookAroundGoal(this));
    }
}
