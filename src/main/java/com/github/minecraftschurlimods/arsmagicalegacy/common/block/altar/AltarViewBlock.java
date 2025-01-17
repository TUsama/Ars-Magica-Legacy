package com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class AltarViewBlock extends AirBlock implements EntityBlock {
    public AltarViewBlock() {
        super(BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AltarViewBlockEntity(pPos, pState);
    }
}
