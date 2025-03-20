package com.iafenvoy.nezha.registry;

import com.iafenvoy.nezha.NeZha;
import com.iafenvoy.nezha.item.block.LotusPlantBlock;
import com.iafenvoy.nezha.item.block.LotusRootBlock;
import com.iafenvoy.nezha.item.block.LotusStemBlock;
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

    public static final RegistrySupplier<Block> LOTUS_ROOT_DIRT = register("lotus_root_dirt", () -> new LotusRootBlock(AbstractBlock.Settings.copy(Blocks.DIRT)), item -> new BlockItem(item, new Item.Settings().arch$tab(NZItemGroups.MAIN)));
    public static final RegistrySupplier<Block> LOTUS_ROOT_MUD = register("lotus_root_mud", () -> new LotusRootBlock(AbstractBlock.Settings.copy(Blocks.MUD)), item -> new BlockItem(item, new Item.Settings().arch$tab(NZItemGroups.MAIN)));
    public static final RegistrySupplier<Block> LOTUS_LEAF = registerNoItem("lotus_leaf", () -> new LotusPlantBlock(AbstractBlock.Settings.copy(Blocks.BIG_DRIPLEAF)));
    public static final RegistrySupplier<Block> LOTUS_LEAF_WITH_FLOWERS = registerNoItem("lotus_leaf_with_flowers", () -> new LotusPlantBlock(AbstractBlock.Settings.copy(Blocks.BIG_DRIPLEAF)));
    public static final RegistrySupplier<Block> LOTUS_LEAF_WITH_SEEDPODS = registerNoItem("lotus_leaf_with_seedpods", () -> new LotusPlantBlock(AbstractBlock.Settings.copy(Blocks.BIG_DRIPLEAF)));
    public static final RegistrySupplier<Block> LOTUS_STEM = registerNoItem("lotus_stem", () -> new LotusStemBlock(AbstractBlock.Settings.copy(Blocks.BIG_DRIPLEAF)));

    public static <T extends Block> RegistrySupplier<T> registerNoItem(String id, Supplier<T> obj) {
        return REGISTRY.register(id, obj);
    }

    public static <T extends Block> RegistrySupplier<T> register(String id, Supplier<T> obj, Function<T, Item> itemFactory) {
        RegistrySupplier<T> r = registerNoItem(id, obj);
        NZItems.register(id, () -> itemFactory.apply(r.get()));
        return r;
    }
}
