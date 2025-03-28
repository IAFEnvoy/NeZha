package com.iafenvoy.nezha.item;

import com.iafenvoy.neptune.accessory.Accessory;
import com.iafenvoy.neptune.accessory.AccessoryManager;
import com.iafenvoy.neptune.util.Color4i;
import com.iafenvoy.neptune.util.RandomHelper;
import com.iafenvoy.nezha.registry.NZItemGroups;
import com.iafenvoy.nezha.registry.NZItems;
import com.iafenvoy.nezha.trail.EntityTrailProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class HotWheelItem extends Item implements Accessory {
    public HotWheelItem() {
        super(new Settings().arch$tab(NZItemGroups.MAIN));
    }

    @Override
    public void tick(ItemStack stack, LivingEntity entity) {
        if (entity.getWorld() instanceof ClientWorld clientWorld)
            for (int i = 0; i < 5; i++)
                clientWorld.addParticle(ParticleTypes.FLAME, true, entity.getX() + RandomHelper.nextDouble(-0.25, 0.25), entity.getY() + RandomHelper.nextDouble(-1, 0), entity.getZ() + RandomHelper.nextDouble(-0.5, 0.5), 0, 0, 0);
    }

    @Override
    public void onEquip(ItemStack stack, LivingEntity entity) {
        if (entity instanceof PlayerEntity player && !player.isCreative()) {
            player.getAbilities().allowFlying = true;
            player.sendAbilitiesUpdate();
        }
        if (entity.getWorld().isClient)
            EntityTrailProvider.builder(entity)
                    .offset(new Vec3d(0, -1.5, 0))
                    .color(new Color4i(255, 69, 0, 255))
                    .width(0.75f)
                    .removeCondition(living -> !AccessoryManager.getEquipped(living, AccessoryManager.Place.FEET).isOf(NZItems.HOT_WHEEL.get()))
                    .build().apply();
    }

    @Override
    public void onUnequip(ItemStack stack, LivingEntity entity) {
        if (entity instanceof PlayerEntity player && !player.isCreative()) {
            player.getAbilities().allowFlying = false;
            player.getAbilities().flying = false;
            player.sendAbilitiesUpdate();
        }
    }

    @Override
    public SoundEvent getEquipSound(ItemStack stack) {
        return SoundEvents.ITEM_FIRECHARGE_USE;
    }
}
