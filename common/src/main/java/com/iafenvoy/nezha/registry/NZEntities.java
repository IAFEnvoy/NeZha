package com.iafenvoy.nezha.registry;

import com.iafenvoy.neptune.util.EntityBuildHelper;
import com.iafenvoy.nezha.NeZha;
import com.iafenvoy.nezha.entity.SkyArrowEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKeys;

public final class NZEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(NeZha.MOD_ID, RegistryKeys.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<SkyArrowEntity>> SKY_ARROW = register("sky_arrow", SkyArrowEntity::new, SpawnGroup.MISC, false, 1, 1);

    private static <T extends Entity> RegistrySupplier<EntityType<T>> register(String entityName, EntityType.EntityFactory<T> constructor, SpawnGroup category, boolean fireImmune, float sizeX, float sizeY) {
        return REGISTRY.register(entityName, EntityBuildHelper.build(entityName, constructor, category, 64, 3, fireImmune, new EntityBuildHelper.Dimension(sizeX, sizeY)));
    }
}
