package com.iafenvoy.nezha.registry;

import com.iafenvoy.neptune.ability.AbilityCategory;
import com.iafenvoy.neptune.util.Color4i;
import com.iafenvoy.nezha.NeZha;
import net.minecraft.util.Identifier;

public final class NZAbilityCategories {
    public static final AbilityCategory MAGIC_ARTS = new AbilityCategory(Identifier.of(NeZha.MOD_ID, "magic_arts"), new Color4i(255, 255, 0, 255), () -> true);

    public static void init() {
    }
}
