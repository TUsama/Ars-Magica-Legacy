package com.github.minecraftschurli.arsmagicalegacy.common.skill;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillManager;
import com.github.minecraftschurli.codeclib.CodecDataManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;


public final class SkillManager extends CodecDataManager<ISkill> implements ISkillManager {
    private static final Lazy<SkillManager> INSTANCE = Lazy.concurrentOf(SkillManager::new);

    private SkillManager() {
        super("am_skills", Skill.CODEC, Skill.NETWORK_CODEC, SkillManager::validateSkills, LogManager.getLogger());
        subscribeAsSyncable(ArsMagicaLegacy.NETWORK_HANDLER);
    }

    @Override
    public Set<ISkill> getSkillsForOcculusTab(ResourceLocation id) {
        return values().stream()
                       .filter(skill -> id.equals(skill.getOcculusTab()))
                       .collect(Collectors.toSet());
    }

    @Override
    public Optional<ISkill> getOptional(ResourceLocation id) {
        return getOptional((Object) id);
    }

    @Override
    public ISkill get(ResourceLocation id) {
        return getOrThrow(id);
    }

    @Nullable
    @Override
    public ISkill getNullable(ResourceLocation id) {
        return get((Object) id);
    }

    @Override
    public Collection<ISkill> getSkills() {
        return values();
    }

    public static SkillManager instance() {
        return INSTANCE.get();
    }

    private static void validateSkills(Map<ResourceLocation, ISkill> data, Logger logger) {
        data.forEach((id, skill) -> ((Skill) skill).setId(id));
        data.values()
            .removeIf(skill -> {
                boolean err = false;
                if (!data.keySet()
                         .containsAll(skill.getParents())) {
                    logger.warn("Skill {} is missing parents {}. It will be removed!",
                                skill.getId(),
                                new HashSet<>(skill.getParents()).removeAll(data.keySet()));
                    err = true;
                }
                var tabOpt = ArsMagicaAPI.get()
                                         .getOcculusTabManager()
                                         .getOptional(skill.getOcculusTab());
                if (tabOpt.isEmpty()) {
                    logger.warn("The occulus tab {} for skill {} is not available. The skill will be removed!",
                                skill.getOcculusTab(),
                                skill.getId());
                    return true;
                }
                var tab = tabOpt.get();
                if (skill.getY() < 0 ||
                    skill.getY() > (tab.getHeight() - 32) ||
                    skill.getX() < 0 ||
                    skill.getX() > (tab.getWidth() - 32)) {
                    logger.warn("Skill {} is outside the bounds of the skill tree. It will be removed!",
                                skill.getId());
                    err = true;
                }
                return err;
            });
    }
}
