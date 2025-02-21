package com.iafenvoy.nezha.fabric;

import net.fabricmc.api.ModInitializer;

import com.iafenvoy.nezha.NeZha;

public final class NeZhaFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NeZha.init();
        NeZha.process();
    }
}
