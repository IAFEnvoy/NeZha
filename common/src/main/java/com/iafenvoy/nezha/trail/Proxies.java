package com.iafenvoy.nezha.trail;

import com.iafenvoy.nezha.trail.proxy.TrailProxy;
import org.jetbrains.annotations.ApiStatus;

public final class Proxies {
    @ApiStatus.Internal
    public static TrailProxy PROXY = TrailProxy.Empty.INSTANCE;
}
