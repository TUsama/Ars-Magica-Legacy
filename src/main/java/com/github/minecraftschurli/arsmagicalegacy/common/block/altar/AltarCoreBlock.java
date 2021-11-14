package com.github.minecraftschurli.arsmagicalegacy.common.block.altar;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class AltarCoreBlock extends Block implements EntityBlock {
    public static final BooleanProperty FORMED = BooleanProperty.create("formed");

    public AltarCoreBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(3));
        registerDefaultState(getStateDefinition().any().setValue(FORMED, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AltarCoreBlockEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FORMED);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return (BlockEntityTicker<T>) new AltarCoreTicker();
    }

    private static class AltarCoreTicker implements BlockEntityTicker<AltarCoreBlockEntity> {
        @Override
        public void tick(Level pLevel, BlockPos pPos, BlockState pState, AltarCoreBlockEntity pBlockEntity) {
            pBlockEntity.checkCounter--;
            if (pBlockEntity.checkCounter <= 0) {
                pBlockEntity.checkMultiblock();
                pBlockEntity.checkCounter = 20;
            }
            if (!pBlockEntity.isMultiblockFormed()) return;
            pBlockEntity.consumeTick();
        }
    }
}