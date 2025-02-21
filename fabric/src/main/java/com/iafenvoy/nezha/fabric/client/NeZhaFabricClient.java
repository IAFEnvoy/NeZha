package com.iafenvoy.nezha.fabric.client;

import com.iafenvoy.nezha.NeZhaClient;
import net.fabricmc.api.ClientModInitializer;

public final class NeZhaFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NeZhaClient.init();
        NeZhaClient.process();
    }
}
