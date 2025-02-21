package com.iafenvoy.nezha.entity;

import com.iafenvoy.nezha.registry.NZEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.world.World;

public class SkyArrowEntity extends ArrowEntity {
    public SkyArrowEntity(EntityType<SkyArrowEntity> type, World world) {
        super(type, world);
    }

    public SkyArrowEntity(World world, LivingEntity owner) {
        super(NZEntities.SKY_ARROW.get(), world);
        this.setOwner(owner);
        this.setPosition(owner.getPos().add(0, 1, 0));
        this.pickupType = PickupPermission.DISALLOWED;
    }
}
