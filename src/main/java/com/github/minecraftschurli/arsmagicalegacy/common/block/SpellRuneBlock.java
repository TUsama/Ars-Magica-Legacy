package com.github.minecraftschurli.arsmagicalegacy.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class SpellRuneBlock extends Block {
    public  static final DirectionProperty          FACE             = BlockStateProperties.FACING;
    private static final Map<Direction, VoxelShape> COLLISION_SHAPES = Map.of(
            Direction.DOWN,  Block.box( 1.0D,  0.0D,  1.0D, 15.0D,  0.5D, 15.0D),
            Direction.UP,    Block.box( 1.0D, 15.5D,  1.0D, 15.0D, 16.0D, 15.0D),
            Direction.NORTH, Block.box( 0.0D,  1.0D,  1.0D,  0.5D, 15.0D, 15.0D),
            Direction.SOUTH, Block.box(15.5D,  1.0D,  1.0D, 16.0D, 15.0D, 15.0D),
            Direction.WEST,  Block.box( 1.0D,  1.0D,  0.0D, 15.0D, 15.0D,  0.5D),
            Direction.EAST,  Block.box( 1.0D,  1.0D, 15.5D, 15.0D, 15.0D, 16.0D)
    );

    public SpellRuneBlock() {
        super(BlockBehaviour.Properties.of(Material.AIR).air());
        registerDefaultState(getStateDefinition().any().setValue(FACE, Direction.DOWN));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACE);
    }

    @Override
    public VoxelShape getShape(BlockState pState,
                                        BlockGetter pLevel,
                                        BlockPos pPos,
                                        CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState,
                               BlockGetter pLevel,
                               BlockPos pPos,
                               CollisionContext pContext) {
        return COLLISION_SHAPES.get(pState.getValue(FACE));
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pLevel.isClientSide()) return;
        ((SpellRuneBlockEntity)pLevel.getBlockEntity(pPos)).collide(pLevel, pPos, pEntity, pState.getValue(FACE));
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.DESTROY;
    }
}
