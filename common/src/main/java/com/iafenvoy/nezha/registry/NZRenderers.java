package com.iafenvoy.nezha.registry;

import com.iafenvoy.neptune.render.CommonPlayerLikeEntityRenderer;
import com.iafenvoy.neptune.render.DynamicItemRenderer;
import com.iafenvoy.neptune.render.armor.IArmorRendererBase;
import com.iafenvoy.nezha.render.entity.NeZhaEntityRenderer;
import com.iafenvoy.nezha.render.entity.SkyArrowEntityRenderer;
import com.iafenvoy.nezha.render.item.HotWheelArmorRenderer;
import com.iafenvoy.nezha.render.item.SkyShieldItemRenderer;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.item.ItemPropertiesRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class NZRenderers {
    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(NZEntities.SKY_ARROW, SkyArrowEntityRenderer::new);

        EntityRendererRegistry.register(NZEntities.NE_ZHA, NeZhaEntityRenderer::new);
        EntityRendererRegistry.register(NZEntities.AO_BING, CommonPlayerLikeEntityRenderer::new);
    }

    public static void registerRenderLayers() {
        RenderTypeRegistry.register(RenderLayer.getCutout(), NZBlocks.LOTUS_LEAF.get(), NZBlocks.LOTUS_LEAF_WITH_FLOWERS.get(), NZBlocks.LOTUS_LEAF_WITH_SEEDPODS.get(), NZBlocks.LOTUS_STEM.get());
    }

    public static void registerModelPredicates() {
        ItemPropertiesRegistry.register(NZItems.SKY_BOW.get(), Identifier.of(Identifier.DEFAULT_NAMESPACE, "pulling"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
        ItemPropertiesRegistry.register(NZItems.SKY_BOW.get(), Identifier.of(Identifier.DEFAULT_NAMESPACE, "pull"), (stack, world, entity, seed) -> entity == null ? 0.0F : entity.getActiveItem() != stack ? 0.0F : (stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 60.0F);
    }

    public static void registerBuiltinItemRenderers() {
        DynamicItemRenderer.RENDERERS.put(NZItems.SKY_SHIELD.get(), new SkyShieldItemRenderer());
    }

    public static void registerArmorRenderers() {
        IArmorRendererBase.register(new HotWheelArmorRenderer(), NZItems.HOT_WHEEL.get());
    }
}
