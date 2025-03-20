package com.iafenvoy.nezha.item.block;

import com.iafenvoy.neptune.util.RandomHelper;
import com.iafenvoy.nezha.registry.NZBlocks;
import com.iafenvoy.nezha.registry.NZTags;
import com.iafenvoy.nezha.util.HorizontalDirectionProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.LinkedList;
import java.util.List;

public class LotusRootBlock extends Block implements Fertilizable, HorizontalDirectionProvider {
    public static final IntProperty GROWTH = IntProperty.of("growth", 0, 3);

    public LotusRootBlock(Settings settings) {
        super(settings.ticksRandomly());
        this.setDefaultState(this.getStateManager().getDefaultState().with(GROWTH, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(GROWTH);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.grow(world, random, pos, state);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        BlockState s = world.getBlockState(pos.up());
        if (s.isIn(NZTags.LOTUS_PLANTS)) return state.get(GROWTH) > 0 && !findAvailableRootPos(world, pos).isEmpty();
        int d = getDepth(world, pos);
        return 0 < d && d < 4;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos up = pos.up();
        if (world.getBlockState(up).isOf(Blocks.WATER))
            world.setBlockState(up, NZBlocks.LOTUS_STEM.get().getDefaultState());
        int growth = state.get(GROWTH);
        List<BlockPos> available = findAvailableRootPos(world, pos);
        if (!available.isEmpty() && growth > 0) {
            growth = Math.max(0, growth - RandomHelper.nextInt(1, 2));
            BlockPos p = RandomHelper.randomOne(available);
            world.setBlockState(p, (isMud(world.getBlockState(p)) ? NZBlocks.LOTUS_ROOT_MUD : NZBlocks.LOTUS_ROOT_DIRT).get().getDefaultState().with(GROWTH, growth));
        }
    }

    public static boolean isMud(BlockState state) {
        return state.isOf(Blocks.MUD) || state.isOf(Blocks.MUDDY_MANGROVE_ROOTS);
    }

    public static List<BlockPos> findAvailableRootPos(WorldView world, BlockPos pos) {
        List<BlockPos> results = new LinkedList<>();
        for (Direction direction : HORIZONTAL) {
            BlockPos target = pos.add(direction.getVector());
            BlockState state = world.getBlockState(target);
            if (state.isIn(BlockTags.DIRT)) {
                BlockPos upPos = target.up();
                BlockState upState = world.getBlockState(upPos);
                if (upState.isOf(Blocks.WATER))
                    results.add(target);
                else if (upState.isIn(BlockTags.DIRT) && world.getBlockState(upPos.up()).isOf(Blocks.WATER))
                    results.add(upPos);
            } else if (state.isOf(Blocks.WATER) && world.getBlockState(target.down()).isIn(BlockTags.DIRT))
                results.add(target.down());
        }
        return results;
    }

    public static int getDepth(WorldView world, BlockPos pos) {
        int depth = 0;
        pos = pos.up();
        while (world.getBlockState(pos).isOf(Blocks.WATER)) {
            depth++;
            pos = pos.up();
        }
        return depth;
    }
}
