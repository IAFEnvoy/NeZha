package com.iafenvoy.nezha.fabric;

import com.iafenvoy.nezha.NeZha;
import net.fabricmc.api.ModInitializer;

public final class NeZhaFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NeZha.init();
        NeZha.process();
    }
}
