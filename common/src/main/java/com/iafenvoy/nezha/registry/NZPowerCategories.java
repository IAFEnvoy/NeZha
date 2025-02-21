package com.iafenvoy.nezha.registry;

import com.iafenvoy.neptune.power.PowerCategory;
import com.iafenvoy.neptune.util.Color4i;
import com.iafenvoy.nezha.NeZha;
import net.minecraft.util.Identifier;

public class NZPowerCategories {
    public static final PowerCategory MAGIC_ARTS = new PowerCategory(Identifier.of(NeZha.MOD_ID, "magic_arts"), new Color4i(255, 255, 0, 255), () -> true);

    public static void init() {
    }
}
