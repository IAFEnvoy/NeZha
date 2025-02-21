package com.iafenvoy.nezha.registry;

import com.iafenvoy.nezha.NeZha;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public final class NZItemGroups {
    public static final DeferredRegister<ItemGroup> REGISTRY = DeferredRegister.create(NeZha.MOD_ID, RegistryKeys.ITEM_GROUP);

    public static final RegistrySupplier<ItemGroup> MAIN = registry("main", () -> CreativeTabRegistry.create(Text.translatable("itemGroup.%s.main".formatted(NeZha.MOD_ID)), Items.AIR::getDefaultStack));

    public static <T extends ItemGroup> RegistrySupplier<T> registry(String id, Supplier<T> obj) {
        return REGISTRY.register(id, obj);
    }
}
