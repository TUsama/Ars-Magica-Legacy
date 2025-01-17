package com.github.minecraftschurlimods.arsmagicalegacy.common.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SkillPoint extends ForgeRegistryEntry<ISkillPoint> implements ISkillPoint {
    private final int color;
    private final int minEarnLevel;
    private final int levelsForPoint;

    public SkillPoint(int color, int minEarnLevel, int levelsForPoint) {
        this.color = color;
        this.minEarnLevel = minEarnLevel;
        this.levelsForPoint = levelsForPoint;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public int getLevelsForPoint() {
        return levelsForPoint;
    }

    @Override
    public int getMinEarnLevel() {
        return minEarnLevel;
    }
}
