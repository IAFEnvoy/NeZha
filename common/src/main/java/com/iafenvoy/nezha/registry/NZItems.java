package com.iafenvoy.nezha.registry;

import com.iafenvoy.neptune.object.item.RestrictedItem;
import com.iafenvoy.nezha.NeZha;
import com.iafenvoy.nezha.entity.NeZhaEntity;
import com.iafenvoy.nezha.item.SkyBowItem;
import com.iafenvoy.nezha.item.impl.SkyShieldItem;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Supplier;

public final class NZItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(NeZha.MOD_ID, RegistryKeys.ITEM);

    public static final RegistrySupplier<Item> SKY_SHIELD = register("sky_shield", SkyShieldItem::create);
    public static final RegistrySupplier<Item> SKY_BOW = register("sky_bow", SkyBowItem::new);
    //Spawn Eggs
    public static final RegistrySupplier<Item> NE_ZHA_SPAWN_EGG = register("ne_zha_spawn_egg", () -> new ArchitecturySpawnEggItem(NZEntities.NE_ZHA, 0xFFF4A460, 0xFFFF0000, new Item.Settings().arch$tab(NZItemGroups.MAIN)));
    public static final RegistrySupplier<Item> AO_BING_SPAWN_EGG = register("ao_bing_spawn_egg", () -> new ArchitecturySpawnEggItem(NZEntities.AO_BING, 0xFFFFFFFF, 0xFF87CEFA, new Item.Settings().arch$tab(NZItemGroups.MAIN)));
    //Delight
    public static final RegistrySupplier<Item> LOTUS_ROOT = register("lotus_root", () -> new Item(new Item.Settings().arch$tab(NZItemGroups.MAIN)));
    public static final RegistrySupplier<Item> LOTUS_ROOT_SLICE = register("lotus_root_slice", () -> new Item(new Item.Settings().arch$tab(NZItemGroups.MAIN)));
    public static final RegistrySupplier<Item> LOTUS_ROOT_STARCH = register("lotus_root_starch", () -> new Item(new Item.Settings().arch$tab(NZItemGroups.MAIN)));
    public static final RegistrySupplier<Item> LOTUS_CAKE = register("lotus_cake", () -> new Item(new Item.Settings().arch$tab(NZItemGroups.MAIN)));
    //Restricted Items
    public static final RegistrySupplier<Item> NE_ZHA_HAIR = register("ne_zha_hair", () -> new RestrictedItem(new Item.Settings(), (stack, world, entity, slot, selected) -> entity instanceof NeZhaEntity neZha && neZha.getEquippedStack(EquipmentSlot.HEAD) == stack));

    public static <T extends Item> RegistrySupplier<T> register(String id, Supplier<T> obj) {
        return REGISTRY.register(id, obj);
    }
}
