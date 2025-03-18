package com.iafenvoy.nezha.registry;

import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public final class NZBehaviours {
    private static final CauldronBehavior CLEAN_LOTUS_ROOT = (state, world, pos, player, hand, stack) -> {
        if (!player.getStackInHand(hand).isIn(NZTags.DIRTY_LOTUS_BLOCK)) return ActionResult.PASS;
        if (!player.isCreative()) stack.decrement(1);
        if (!world.isClient) {
            player.getInventory().offerOrDrop(new ItemStack(NZItems.LOTUS_ROOT.get(), world.getRandom().nextBetween(1, 3)));
            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        }
        return ActionResult.SUCCESS;
    };

    public static void init() {
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(NZBlocks.LOTUS_ROOT_DIRT.get().asItem(), CLEAN_LOTUS_ROOT);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(NZBlocks.LOTUS_ROOT_MUD.get().asItem(), CLEAN_LOTUS_ROOT);
    }
}
