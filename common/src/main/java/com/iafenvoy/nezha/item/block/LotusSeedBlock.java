package com.iafenvoy.nezha.item.block;

import com.iafenvoy.neptune.util.RandomHelper;
import com.iafenvoy.nezha.registry.NZBlocks;
import com.iafenvoy.nezha.registry.NZTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;

import java.util.List;

public class LotusSeedBlock extends AbstractLotusRootBlock {
    public LotusSeedBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        BlockState s = world.getBlockState(pos.up());
        if (s.isIn(NZTags.LOTUS_PLANTS)) return !findAvailableRootPos(world, pos).isEmpty();
        int d = getDepth(world, pos);
        return 0 < d && d < 4;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos up = pos.up();
        if (world.getBlockState(up).isOf(Blocks.WATER))
            world.setBlockState(up, NZBlocks.LOTUS_STEM.get().getDefaultState());
        List<BlockPos> available = findAvailableRootPos(world, pos);
        if (!available.isEmpty()) {
            int growth = Math.max(0, 4 - RandomHelper.nextInt(1, 2));
            BlockPos p = RandomHelper.randomOne(available);
            world.setBlockState(p, (isMud(world.getBlockState(p)) ? NZBlocks.LOTUS_ROOT_MUD : NZBlocks.LOTUS_ROOT_DIRT).get().getDefaultState().with(LotusRootBlock.GROWTH, growth));
        }
    }
}
