package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.SOUND_EVENTS;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds.*;

class AMSoundDefinitionsProvider extends SoundDefinitionsProvider {

    private final Set<ResourceLocation> sounds = new HashSet<>();

    AMSoundDefinitionsProvider(final DataGenerator generator, final ExistingFileHelper helper) {
        super(generator, ArsMagicaAPI.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        noSound(AIR_GUARDIAN_AMBIENT);
        noSound(AIR_GUARDIAN_ATTACK);
        noSound(AIR_GUARDIAN_DEATH);
        noSound(AIR_GUARDIAN_HURT);
        auto(ARCANE_GUARDIAN_ATTACK, 5);
        auto(LIGHTNING_GUARDIAN_LIGHTNING_ROD, 3);
        noSound(WATER_GUARDIAN_ATTACK);
        noSound(WATER_GUARDIAN_HURT);
        noSound(LOOP_NONE);
        SOUND_EVENTS.getEntries().forEach(this::singleSound);
    }

    private void auto(RegistryObject<SoundEvent> supplier, int sounds) {
        if (sounds <= 0) return;
        ResourceLocation location = supplier.getId();
        if (this.sounds.contains(location)) return;
        this.sounds.add(location);
        String subtitle = "subtitle." + location.getNamespace() + "." + location.getPath();
        String sound = location.toString().replace('.', '/');
        if (sounds == 1) {
            add(supplier, definition().with(sound(sound)).subtitle(subtitle));
        } else {
            SoundDefinition def = definition();
            for (int value = 1; value < sounds - 1; value++) {
                def.with(sound(sound + "_" + value));
            }
            add(supplier, def.subtitle(subtitle));
        }
    }

    private void noSound(RegistryObject<SoundEvent> supplier) {
        this.sounds.add(supplier.getId());
    }

    private void singleSound(RegistryObject<SoundEvent> supplier) {
        auto(supplier, 1);
    }
}
