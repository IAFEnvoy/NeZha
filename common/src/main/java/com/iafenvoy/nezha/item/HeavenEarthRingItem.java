package com.iafenvoy.nezha.item;

import com.iafenvoy.nezha.registry.NZItemGroups;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;

public class HeavenEarthRingItem extends Item implements Equipment {
    public HeavenEarthRingItem() {
        super(new Settings().maxCount(1).arch$tab(NZItemGroups.MAIN));
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.HEAD;
    }
}
