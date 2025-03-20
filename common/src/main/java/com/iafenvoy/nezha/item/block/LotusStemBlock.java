package com.iafenvoy.nezha.item.block;

import com.iafenvoy.nezha.registry.NZBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class LotusStemBlock extends LotusPlantBlock implements Fertilizable, FluidFillable {
    private static final VoxelShape SHAPE = VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 1, 0.625);

    public LotusStemBlock(Settings settings) {
        super(settings);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getStill(false);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        BlockState up = world.getBlockState(pos.up());
        return up.isAir() || up.isOf(Blocks.WATER);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos up = pos.up();
        BlockState upState = world.getBlockState(up);
        if (upState.isOf(Blocks.WATER))
            world.setBlockState(up, this.withRandomRotation(this.getDefaultState(), FACING));
        else if (upState.isAir()) world.setBlockState(up, this.withRandomRotation((switch (random.nextInt(3)) {
            case 1 -> NZBlocks.LOTUS_LEAF_WITH_FLOWERS;
            case 2 -> NZBlocks.LOTUS_LEAF_WITH_SEEDPODS;
            default -> NZBlocks.LOTUS_LEAF;
        }).get().getDefaultState(), FACING));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.grow(world, random, pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d offset = state.getModelOffset(world, pos);
        return SHAPE.offset(offset.x, offset.y, offset.z);
    }

    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }
}
