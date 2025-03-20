package com.iafenvoy.nezha.item;

import com.iafenvoy.nezha.item.block.AbstractLotusRootBlock;
import com.iafenvoy.nezha.registry.NZBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LotusSeedItem extends Item {
    public LotusSeedItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        BlockState state = world.getBlockState(pos);
        if (state.isIn(BlockTags.DIRT)) {
            world.setBlockState(pos, (AbstractLotusRootBlock.isMud(state) ? NZBlocks.LOTUS_SEED_MUD : NZBlocks.LOTUS_SEED_DIRT).get().getDefaultState());
            return ActionResult.SUCCESS;
        }
        return super.useOnBlock(context);
    }
}
