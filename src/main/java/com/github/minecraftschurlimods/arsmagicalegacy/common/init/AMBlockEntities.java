package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarViewBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.blackaurem.BlackAuremBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism.CelestialPrismBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.spellrune.SpellRuneBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.BLOCK_ENTITIES;

@NonExtendable
public interface AMBlockEntities {
    RegistryObject<BlockEntityType<AltarCoreBlockEntity>>        ALTAR_CORE        = BLOCK_ENTITIES.register("altar_core",        () -> BlockEntityType.Builder.of(AltarCoreBlockEntity::new,        AMBlocks.ALTAR_CORE.get())       .build(null));
    RegistryObject<BlockEntityType<AltarViewBlockEntity>>        ALTAR_VIEW        = BLOCK_ENTITIES.register("altar_view",        () -> BlockEntityType.Builder.of(AltarViewBlockEntity::new,        AMBlocks.ALTAR_VIEW.get())       .build(null));
    RegistryObject<BlockEntityType<BlackAuremBlockEntity>>       BLACK_AUREM       = BLOCK_ENTITIES.register("black_aurem",       () -> BlockEntityType.Builder.of(BlackAuremBlockEntity::new,       AMBlocks.BLACK_AUREM.get())      .build(null));
    RegistryObject<BlockEntityType<CelestialPrismBlockEntity>>   CELESTIAL_PRISM   = BLOCK_ENTITIES.register("celestial_prism",   () -> BlockEntityType.Builder.of(CelestialPrismBlockEntity::new,   AMBlocks.CELESTIAL_PRISM.get())  .build(null));
    RegistryObject<BlockEntityType<InscriptionTableBlockEntity>> INSCRIPTION_TABLE = BLOCK_ENTITIES.register("inscription_table", () -> BlockEntityType.Builder.of(InscriptionTableBlockEntity::new, AMBlocks.INSCRIPTION_TABLE.get()).build(null));
    RegistryObject<BlockEntityType<ObeliskBlockEntity>>          OBELISK           = BLOCK_ENTITIES.register("obelisk",           () -> BlockEntityType.Builder.of(ObeliskBlockEntity::new,          AMBlocks.OBELISK.get())          .build(null));
    RegistryObject<BlockEntityType<SpellRuneBlockEntity>>        SPELL_RUNE        = BLOCK_ENTITIES.register("spell_rune",        () -> BlockEntityType.Builder.of(SpellRuneBlockEntity::new,        AMBlocks.SPELL_RUNE.get())       .build(null));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
