package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.advancement.PlayerLearnedSkillTrigger;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

class AMAdvancementProvider extends AdvancementProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGenerator generator;
    private final ImmutableList<Consumer<Consumer<Advancement>>> tabs;

    AMAdvancementProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper, AMSkillProvider skillProvider) {
        super(pGenerator, existingFileHelper);
        generator = pGenerator;
        tabs = ImmutableList.of(new AMBookAdvancements(skillProvider));
    }

    @Override
    public void run(HashCache pCache) {
        Path path = generator.getOutputFolder();
        Set<ResourceLocation> set = new HashSet<>();
        Consumer<Advancement> consumer = a -> {
            if (!set.add(a.getId())) throw new IllegalStateException("Duplicate advancement " + a.getId());
            else {
                Path path1 = path.resolve("data/" + a.getId().getNamespace() + "/advancements/" + a.getId().getPath() + ".json");
                try {
                    DataProvider.save(GSON, pCache, a.deconstruct().serializeToJson(), path1);
                } catch (IOException e) {
                    LOGGER.error("Couldn't save advancement {}", path1, e);
                }
            }
        };
        for (Consumer<Consumer<Advancement>> c : tabs) {
            c.accept(consumer);
        }
    }

    /**
     * Contains all advancements that are relevant for book locking.
     */
    private record AMBookAdvancements(AMSkillProvider skillProvider) implements Consumer<Consumer<Advancement>> {
        @Override
        public void accept(Consumer<Advancement> consumer) {
            Advancement root = Advancement.Builder.advancement()
                    .addCriterion("arcane_compendium", InventoryChangeTrigger.TriggerInstance.hasItems(ArsMagicaAPI.get().getBookStack().getItem()))
                    .save(consumer, ArsMagicaAPI.MOD_ID + ":book/root");
            for (ResourceLocation skill : skillProvider.getSkills()) {
                Advancement.Builder.advancement().parent(root)
                        .addCriterion("knows", new PlayerLearnedSkillTrigger.TriggerInstance(EntityPredicate.Composite.ANY, skill))
                        .save(consumer, ArsMagicaAPI.MOD_ID + ":book/" + skill.getPath());
            }
        }
    }
}
