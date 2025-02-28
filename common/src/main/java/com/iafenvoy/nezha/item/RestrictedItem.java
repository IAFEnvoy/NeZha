package com.iafenvoy.nezha.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.apache.commons.lang3.function.ToBooleanBiFunction;

public class RestrictedItem extends Item {
    private final ToBooleanBiFunction<ItemStack, Entity> predicate;

    public RestrictedItem(Settings settings, ToBooleanBiFunction<ItemStack, Entity> predicate) {
        super(settings);
        this.predicate = predicate;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!stack.isOf(this)) return;
        if (this.predicate.applyAsBoolean(stack, entity)) return;
        if (this.isDamageable()) stack.setDamage(this.getMaxDamage() + 1);
        stack.setCount(0);
    }
}
