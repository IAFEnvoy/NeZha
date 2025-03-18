package com.iafenvoy.nezha.registry;

import com.iafenvoy.neptune.ability.type.PersistAbility;
import com.iafenvoy.nezha.NeZha;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public final class NZAbilities {
    public static final PersistAbility SKY_SHIELD = new PersistAbility(Identifier.of(NeZha.MOD_ID, "sky_shield"), NZAbilityCategories.MAGIC_ARTS)
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
