package com.iafenvoy.nezha.item.block;

import com.iafenvoy.nezha.util.HorizontalDirectionProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractLotusRootBlock extends Block implements Fertilizable, HorizontalDirectionProvider {
    public AbstractLotusRootBlock(Settings settings) {
        super(settings.ticksRandomly());
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.grow(world, random, pos, state);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public static boolean isMud(BlockState state) {
        return state.isOf(Blocks.MUD) || state.isOf(Blocks.MUDDY_MANGROVE_ROOTS);
    }

    protected static List<BlockPos> findAvailableRootPos(WorldView world, BlockPos pos) {
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

    protected static int getDepth(WorldView world, BlockPos pos) {
        int depth = 0;
        pos = pos.up();
        while (world.getBlockState(pos).isOf(Blocks.WATER)) {
            depth++;
            pos = pos.up();
        }
        return depth;
    }
}
