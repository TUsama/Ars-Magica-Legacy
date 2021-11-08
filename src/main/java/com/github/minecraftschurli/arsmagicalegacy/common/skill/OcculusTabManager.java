package com.github.minecraftschurli.arsmagicalegacy.common.skill;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.occulus.IOcculusTab;
import com.github.minecraftschurli.arsmagicalegacy.api.occulus.IOcculusTabManager;
import com.github.minecraftschurli.codeclib.CodecDataManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public final class OcculusTabManager extends CodecDataManager<IOcculusTab> implements IOcculusTabManager {
    private static final Supplier<OcculusTabManager> INSTANCE = Lazy.concurrentOf(OcculusTabManager::new);

    private OcculusTabManager() {
        super("occulus_tabs",
              OcculusTab.CODEC,
              OcculusTab.NETWORK_CODEC,
              OcculusTabManager::validate,
              LogManager.getLogger());
        subscribeAsSyncable(ArsMagicaLegacy.NETWORK_HANDLER);
    }

    private static void validate(Map<ResourceLocation, IOcculusTab> data, Logger logger) {
        data.forEach((id, tab) -> ((OcculusTab) tab).setId(id));
    }

    public static OcculusTabManager instance() {
        return INSTANCE.get();
    }

    @Override
    public Optional<IOcculusTab> getOptional(ResourceLocation id) {
        return getOptional((Object) id);
    }

    @Nullable
    @Override
    public IOcculusTab getNullable(ResourceLocation id) {
        return get((Object) id);
    }

    @Override
    public IOcculusTab get(ResourceLocation id) {
        return getOrThrow(id);
    }

    @Override
    public Collection<IOcculusTab> getTabs() {
        return values();
    }

    @Override
    public IOcculusTab getByIndex(int index) {
        return values().stream()
                       .sorted(Comparator.comparing(IOcculusTab::getOcculusIndex))
                       .toArray(IOcculusTab[]::new)[index];
    }
}
