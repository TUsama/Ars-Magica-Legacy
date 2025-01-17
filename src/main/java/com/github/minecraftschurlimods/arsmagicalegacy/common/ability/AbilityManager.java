package com.github.minecraftschurlimods.arsmagicalegacy.common.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbilityData;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbilityManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.Range;
import com.github.minecraftschurlimods.codeclib.CodecDataManager;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AbilityManager extends CodecDataManager<IAbilityData> implements IAbilityManager {
    private static final Lazy<AbilityManager> INSTANCE = Lazy.concurrentOf(AbilityManager::new);

    private AbilityManager() {
        super("affinity_abilities", AbilityData.CODEC, (data, logger) -> {
            IForgeRegistry<IAffinity> affinityRegistry = ArsMagicaAPI.get().getAffinityRegistry();
            data.keySet().removeIf(key -> !affinityRegistry.containsKey(key));
        }, LogManager.getLogger());
    }

    @Override
    public List<ResourceLocation> getAbilitiesForPlayer(Player player) {
        return entrySet().stream().filter(e -> e.getValue().test(player)).map(Entry::getKey).collect(Collectors.toList());
    }

    @Override
    public List<ResourceLocation> getAbilitiesForAffinity(IAffinity affinity) {
        return getAbilitiesForAffinity(affinity.getId());
    }

    @Override
    public List<ResourceLocation> getAbilitiesForAffinity(ResourceLocation affinity) {
        return keySet().stream().filter(key -> key.equals(affinity)).collect(Collectors.toList());
    }

    @Override
    public Optional<IAbilityData> getOptional(ResourceLocation id) {
        return super.getOptional(id);
    }

    @Override
    public IAbilityData get(ResourceLocation id) {
        return super.getOrThrow(id);
    }

    @Nullable
    @Override
    public IAbilityData getNullable(ResourceLocation id) {
        return super.get(id);
    }

    /**
     * @return The instance of the ability manager.
     */
    public static AbilityManager instance() {
        return INSTANCE.get();
    }

    private record AbilityData(IAffinity affinity, Range range) implements IAbilityData {
        private static final Codec<IAbilityData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                CodecHelper.forRegistry(ArsMagicaAPI.get()::getAffinityRegistry).fieldOf("affinity").forGetter(IAbilityData::affinity),
                Range.CODEC.fieldOf("range").forGetter(IAbilityData::range)
        ).apply(inst, AbilityData::new));
    }
}
