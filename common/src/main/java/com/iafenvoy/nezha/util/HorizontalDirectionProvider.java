package com.iafenvoy.nezha.util;

import com.iafenvoy.neptune.util.RandomHelper;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.Direction;

public interface HorizontalDirectionProvider {
    Direction[] HORIZONTAL = new Direction[]{Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH};

    default BlockState withRandomRotation(BlockState state, DirectionProperty property) {
        return state.with(property, RandomHelper.randomOne(HORIZONTAL));
    }
}
