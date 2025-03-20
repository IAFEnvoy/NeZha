package com.iafenvoy.nezha.registry;

import com.iafenvoy.nezha.NeZha;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class NZTags {
    public static final TagKey<Item> DIRTY_LOTUS_BLOCK = TagKey.of(RegistryKeys.ITEM, Identifier.of(NeZha.MOD_ID, "dirty_lotus_block"));

    public static final TagKey<Block> LOTUS_PLANT_CAN_PLACE_AT = TagKey.of(RegistryKeys.BLOCK, Identifier.of(NeZha.MOD_ID, "lotus_plant_can_place_at"));
    public static final TagKey<Block> LOTUS_PLANTS = TagKey.of(RegistryKeys.BLOCK, Identifier.of(NeZha.MOD_ID, "lotus_plants"));
}
