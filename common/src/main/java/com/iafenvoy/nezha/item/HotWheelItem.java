package com.iafenvoy.nezha.item;

import com.iafenvoy.neptune.accessory.Accessory;
import com.iafenvoy.neptune.trail.TrailManager;
import com.iafenvoy.neptune.util.RandomHelper;
import com.iafenvoy.nezha.NeZha;
import com.iafenvoy.nezha.registry.NZItemGroups;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class HotWheelItem extends Item implements Accessory {
    public static final Identifier TRIAL_ID = Identifier.of(NeZha.MOD_ID, "hot_wheel");

    public HotWheelItem() {
        super(new Settings().arch$tab(NZItemGroups.MAIN));
    }

    @Override
    public void tick(ItemStack stack, LivingEntity entity) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20, 0));
        World world = entity.getWorld();
        BlockPos pos = entity.getBlockPos().down();
        if (AbstractFireBlock.canPlaceAt(world, pos, Direction.UP))
            world.setBlockState(pos, AbstractFireBlock.getState(world, pos), 11);
        if (world instanceof ClientWorld clientWorld)
            for (int i = 0; i < 5; i++)
                clientWorld.addParticle(ParticleTypes.FLAME, true, entity.getX() + RandomHelper.nextDouble(-0.25, 0.25), entity.getY() + RandomHelper.nextDouble(-1, 0), entity.getZ() + RandomHelper.nextDouble(-0.5, 0.5), 0, 0, 0);
    }

    @Override
    public void onEquip(ItemStack stack, LivingEntity entity) {
        if (entity instanceof PlayerEntity player && !player.isCreative()) {
            player.getAbilities().allowFlying = true;
            player.sendAbilitiesUpdate();
        }
        TrailManager.addTrail(entity, TRIAL_ID);
    }

    @Override
    public void onUnequip(ItemStack stack, LivingEntity entity) {
        if (entity instanceof PlayerEntity player && !player.isCreative()) {
            player.getAbilities().allowFlying = false;
            player.getAbilities().flying = false;
            player.sendAbilitiesUpdate();
        }
        TrailManager.removeTrail(entity, TRIAL_ID);
    }

    @Override
    public SoundEvent getEquipSound(ItemStack stack) {
        return SoundEvents.ITEM_FIRECHARGE_USE;
    }
}
