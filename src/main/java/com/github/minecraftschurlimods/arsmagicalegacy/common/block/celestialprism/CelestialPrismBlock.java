package com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.BiPredicate;

@SuppressWarnings("deprecation")
public class CelestialPrismBlock extends BaseEntityBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    private static final VoxelShape BOX = Block.box(2, 0, 2, 14, 16, 14);
    public final BiPredicate<Level, BlockPos> CHALK = PatchouliCompat.getMultiblockMatcher(PatchouliCompat.CELESTIAL_PRISM_CHALK);
    public final BiPredicate<Level, BlockPos> PILLAR1 = PatchouliCompat.getMultiblockMatcher(PatchouliCompat.CELESTIAL_PRISM_PILLAR1);
    public final BiPredicate<Level, BlockPos> PILLAR2 = PatchouliCompat.getMultiblockMatcher(PatchouliCompat.CELESTIAL_PRISM_PILLAR2);
    public final BiPredicate<Level, BlockPos> PILLAR3 = PatchouliCompat.getMultiblockMatcher(PatchouliCompat.CELESTIAL_PRISM_PILLAR3);
    public final BiPredicate<Level, BlockPos> PILLAR4 = PatchouliCompat.getMultiblockMatcher(PatchouliCompat.CELESTIAL_PRISM_PILLAR4);

    public CelestialPrismBlock() {
        super(BlockBehaviour.Properties.of(Material.AMETHYST).lightLevel(value -> 1).noOcclusion().emissiveRendering((p_61036_, p_61037_, p_61038_) -> true));
        registerDefaultState(getStateDefinition().any().setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BOX;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? AMBlockEntities.CELESTIAL_PRISM.get().create(pos, state) : null;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return !level.isClientSide() && state.getValue(HALF) == DoubleBlockHalf.LOWER ? BaseEntityBlock.createTickerHelper(blockEntityType, AMBlockEntities.CELESTIAL_PRISM.get(), (pLevel, pPos, pState, pBlockEntity) -> pBlockEntity.tick(pLevel, pPos, pState)) : null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HALF);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            level.setBlock(pos.above(), defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), UPDATE_ALL);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getLevel().isOutsideBuildHeight(context.getClickedPos())) return null;
        if (context.getLevel().isOutsideBuildHeight(context.getClickedPos().above())) return null;
        if (!context.getLevel().getBlockState(context.getClickedPos()).canBeReplaced(context)) return null;
        if (!context.getLevel().getBlockState(context.getClickedPos().above()).canBeReplaced(context)) return null;
        return super.getStateForPlacement(context);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        BlockPos blockpos = doubleblockhalf == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.getBlock() == this && blockstate.getValue(HALF) != doubleblockhalf) {
            level.setBlock(blockpos, Blocks.AIR.defaultBlockState(), UPDATE_SUPPRESS_DROPS | UPDATE_ALL);
            level.levelEvent(player, 2001, blockpos, Block.getId(level.getBlockState(blockpos)));
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    /**
     * @param state The state of the core block.
     * @param world The world this block is in.
     * @param pos   The position of the core block.
     * @return The tier of the surrounding multiblock.
     */
    public int getTier(BlockState state, Level world, BlockPos pos) {
        int tier = 0;
        if (CHALK.test(world, pos)) {
            if (PILLAR1.test(world, pos)) {
                tier = 2;
            } else if (PILLAR2.test(world, pos)) {
                tier = 3;
            } else if (PILLAR3.test(world, pos)) {
                tier = 4;
            } else if (PILLAR4.test(world, pos)) {
                tier = 5;
            } else {
                tier = 1;
            }
        }
        return tier;
    }
}
