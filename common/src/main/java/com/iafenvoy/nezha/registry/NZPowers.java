package com.iafenvoy.nezha.registry;

import com.iafenvoy.neptune.power.type.PersistPower;
import com.iafenvoy.nezha.NeZha;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public final class NZPowers {
    public static final PersistPower SKY_SHIELD = new PersistPower(Identifier.of(NeZha.MOD_ID, "sky_shield"), NZPowerCategories.MAGIC_ARTS)
            .setExhaustion(holder -> 0.02)
            .onApply(holder -> {
                ItemStack stack = new ItemStack(NZItems.SKY_SHIELD.get());
                holder.getPlayer().setStackInHand(Hand.OFF_HAND, stack);
            }).onTick(holder -> {
                if (!holder.getPlayer().getOffHandStack().isOf(NZItems.SKY_SHIELD.get())) holder.cancel();
            }).onUnapply(holder -> {
                if (holder.getPlayer().getOffHandStack().isOf(NZItems.SKY_SHIELD.get()))
                    holder.getPlayer().setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY.copy());
            });

    public static void init() {
    }
}
