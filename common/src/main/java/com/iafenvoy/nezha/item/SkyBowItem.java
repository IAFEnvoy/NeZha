package com.iafenvoy.nezha.item;

import com.iafenvoy.nezha.entity.SkyArrowEntity;
import com.iafenvoy.nezha.registry.NZItemGroups;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class SkyBowItem extends BowItem {
    public SkyBowItem() {
        super(new Settings().maxDamage(10000).arch$tab(NZItemGroups.MAIN));
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            int i = this.getMaxUseTime(stack) - remainingUseTicks;
            float f = getPullProgress(i);
            if (f >= 0.1) {
                if (!world.isClient) {
                    SkyArrowEntity arrow = new SkyArrowEntity(world, user);
                    arrow.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, f * 3.0F, 1.0F);
                    if (f == 1.0F) arrow.setCritical(true);
                    int j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                    if (j > 0) arrow.setDamage(arrow.getDamage() + (double) j * 0.5 + 1.5);
                    int k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                    if (k > 0) arrow.setPunch(k);
                    if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0)
                        arrow.setOnFireFor(100);
                    stack.damage(1, playerEntity, p -> p.sendToolBreakStatus(playerEntity.getActiveHand()));
                    world.spawnEntity(arrow);
                }
                world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
            }
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 24000;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return stack -> false;
    }
}
