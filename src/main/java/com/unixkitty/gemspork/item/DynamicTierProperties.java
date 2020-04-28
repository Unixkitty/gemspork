package com.unixkitty.gemspork.item;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.LazyValue;

public abstract class DynamicTierProperties
{
    protected final LazyValue<Ingredient> repairItem;

    public DynamicTierProperties(Tag<Item> repaitItem)
    {
        this.repairItem = new LazyValue<>(() -> Ingredient.fromTag(repaitItem));
    }

    protected static int calcEnchantability(int tierIndex, int tiersTotal, int floor, int ceiling, float floorBump, int enchantability)
    {
        return enchantability == -1 ? getTierStrengthAsInt(tierIndex, tiersTotal, floor, ceiling, floorBump) : enchantability;
    }

    protected static int getTierStrengthAsInt(int tierIndex, int tiersTotal, float floor, float ceil, float floorBump)
    {
        return (int) getTierStrength(tierIndex, tiersTotal, floor, ceil, floorBump);
    }

    protected static float getTierStrength(int tierIndex, int tiersTotal, float floor, float ceil, float floorBump)
    {
        final float FLOOR_BUMP_MIN = 0.1f;
        final float FLOOR_BUMB_MAX = 0.9f;

        floorBump = floor + (ceil - floor) * (floorBump <= 0f ? FLOOR_BUMP_MIN : (floorBump >= 1f ? FLOOR_BUMB_MAX : floorBump));

        return floorBump + (ceil - floorBump) * ((float) tierIndex / tiersTotal);
    }
}
