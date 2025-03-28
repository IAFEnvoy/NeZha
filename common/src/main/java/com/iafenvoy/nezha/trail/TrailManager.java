package com.iafenvoy.nezha.trail;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.world.entity.EntityLike;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@Environment(EnvType.CLIENT)
public class TrailManager {
    private static final List<Function<Entity, Optional<TrailEffect>>> CONSTRUCTORS = new LinkedList<>();

    public static Stream<TrailEffect> collect(EntityLike entity) {
        if (entity instanceof Entity trueEntity)
            return CONSTRUCTORS.stream().map(x -> x.apply(trueEntity)).filter(Optional::isPresent).map(Optional::get);
        return Stream.empty();
    }

    public static void register(Function<Entity, Optional<TrailEffect>> constructor) {
        CONSTRUCTORS.add(constructor);
    }
}
