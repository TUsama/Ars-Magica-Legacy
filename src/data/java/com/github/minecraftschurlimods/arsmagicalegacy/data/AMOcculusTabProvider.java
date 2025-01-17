package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.OcculusTabBuilder;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.OcculusTabProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus.OcculusAffinityTabRenderer;
import net.minecraft.data.DataGenerator;

import java.util.function.Consumer;

class AMOcculusTabProvider extends OcculusTabProvider {
    AMOcculusTabProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void createOcculusTabs(Consumer<OcculusTabBuilder> consumer) {
        createOcculusTab("offense", 0).setStartX(226).setStartY(46).build(consumer);
        createOcculusTab("defense", 1).setStartX(181).setStartY(46).build(consumer);
        createOcculusTab("utility", 2).setStartX(136).setStartY(46).build(consumer);
        createOcculusTab("affinity", 3).setRenderer(OcculusAffinityTabRenderer.class).build(consumer);
    }
}
