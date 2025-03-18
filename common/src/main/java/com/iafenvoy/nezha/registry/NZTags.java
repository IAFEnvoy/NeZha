package com.iafenvoy.nezha.registry;

import com.iafenvoy.nezha.NeZha;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class NZTags {
    public static final TagKey<Item> DIRTY_LOTUS_BLOCK = TagKey.of(RegistryKeys.ITEM, Identifier.of(NeZha.MOD_ID, "dirty_lotus_block"));
}
