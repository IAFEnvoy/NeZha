package com.iafenvoy.nezha.registry;

import com.iafenvoy.nezha.NeZha;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;
import java.util.function.Supplier;

public final class NZBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(NeZha.MOD_ID, RegistryKeys.BLOCK);

    public static final RegistrySupplier<Block> LOTUS_ROOT_DIRT = register("lotus_root_dirt", () -> new Block(AbstractBlock.Settings.copy(Blocks.DIRT)), item -> new BlockItem(item, new Item.Settings().arch$tab(NZItemGroups.MAIN)));
    public static final RegistrySupplier<Block> LOTUS_ROOT_MUD = register("lotus_root_mud", () -> new Block(AbstractBlock.Settings.copy(Blocks.MUD)), item -> new BlockItem(item, new Item.Settings().arch$tab(NZItemGroups.MAIN)));

    public static <T extends Block> RegistrySupplier<T> register(String id, Supplier<T> obj, Function<T, Item> itemFactory) {
        RegistrySupplier<T> r = REGISTRY.register(id, obj);
        NZItems.register(id, () -> itemFactory.apply(r.get()));
        return r;
    }
}
