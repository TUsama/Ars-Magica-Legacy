package com.github.minecraftschurli.arsmagicalegacy.common.item;

import com.google.common.collect.Maps;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

import java.util.Map;

/**
 * Mostly taken from {@link net.minecraft.world.item.DyeItem}
 */
public class ColoredRuneItem extends Item {
    private static final Map<DyeColor, ColoredRuneItem> ITEM_BY_COLOR = Maps.newEnumMap(DyeColor.class);
    private final DyeColor dyeColor;

    public ColoredRuneItem(Properties pProperties, DyeColor dyeColor) {
        super(pProperties);
        this.dyeColor = dyeColor;
        ITEM_BY_COLOR.put(dyeColor, this);
    }

    public DyeColor getDyeColor() {
        return this.dyeColor;
    }

    public static ColoredRuneItem byColor(DyeColor pColor) {
        return ITEM_BY_COLOR.get(pColor);
    }
}