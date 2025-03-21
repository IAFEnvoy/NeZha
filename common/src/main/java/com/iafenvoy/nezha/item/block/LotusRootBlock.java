package com.iafenvoy.nezha.item.block;

import com.iafenvoy.neptune.util.RandomHelper;
import com.iafenvoy.nezha.registry.NZBlocks;
import com.iafenvoy.nezha.registry.NZTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;

import java.util.List;

public class LotusRootBlock extends AbstractLotusRootBlock {
    public static final IntProperty GROWTH = IntProperty.of("growth", 0, 3);

    public LotusRootBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(GROWTH, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(GROWTH);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        BlockState s = world.getBlockState(pos.up());
        if (s.isIn(NZTags.LOTUS_PLANTS)) return state.get(GROWTH) > 0 && !findAvailableRootPos(world, pos).isEmpty();
        int d = getDepth(world, pos);
        return 0 < d && d < 4;
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
}
