package com.github.minecraftschurli.arsmagicalegacy.common.block.altar;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlockEntities;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class AltarViewBlockEntity extends BlockEntity {
    public int       itemRotation;
    public ItemStack itemCache;
    Optional<BlockPos> altar = Optional.empty();

    public AltarViewBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AMBlockEntities.ALTAR_VIEW.get(), pWorldPosition, pBlockState);
    }

    void setAltarPos(BlockPos pos) {
        this.altar = Optional.of(pos);
    }

    public Optional<AltarCoreBlockEntity> getAltar() {
        return this.altar.map(blockPos -> this.level != null && this.level.getBlockEntity(blockPos) instanceof AltarCoreBlockEntity ca ? ca : null);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = super.serializeNBT();
        this.altar.ifPresent(blockPos -> compoundTag.put("altar", BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, blockPos).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn)));
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        if (nbt.contains("altar")) {
            setAltarPos(BlockPos.CODEC.decode(NbtOps.INSTANCE, nbt.get("altar")).map(Pair::getFirst).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn));
        }
    }
}