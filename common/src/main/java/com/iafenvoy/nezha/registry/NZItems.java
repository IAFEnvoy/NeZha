package com.iafenvoy.nezha.registry;

import com.iafenvoy.nezha.NeZha;
import com.iafenvoy.nezha.item.SkyBowItem;
import com.iafenvoy.nezha.item.impl.SkyShieldItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Supplier;

public final class NZItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(NeZha.MOD_ID, RegistryKeys.ITEM);

    public static final RegistrySupplier<Item> SKY_SHIELD = register("sky_shield", SkyShieldItem::create);
    public static final RegistrySupplier<Item> SKY_BOW = register("sky_bow", SkyBowItem::new);

    public static <T extends Item> RegistrySupplier<T> register(String id, Supplier<T> obj) {
        return REGISTRY.register(id, obj);
    }
}
