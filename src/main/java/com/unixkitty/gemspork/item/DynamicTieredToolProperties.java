package com.unixkitty.gemspork.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;

public class DynamicTieredToolProperties extends DynamicTierProperties implements IItemTier
{
    private final int harvestLevel;
    private final int enchantability;

    private final int durability;
    private final float efficiency;
    private final float attackDamage;

    public DynamicTieredToolProperties(int tierIndex, int tiersTotal, int harvestLevel, int enchantability, float floorBump, IItemTier floorTier, IItemTier ceilingTier, ITag<Item> repairItem)
    {
        super(repairItem);

        this.harvestLevel = harvestLevel == -1 ? (tiersTotal == (tierIndex + 1) ? ceilingTier.getLevel() : floorTier.getLevel()) : harvestLevel;
        this.enchantability = calcEnchantability(tierIndex, tiersTotal, floorTier.getEnchantmentValue(), ceilingTier.getEnchantmentValue(), floorBump, enchantability);

        this.durability = getTierStrengthAsInt(tierIndex, tiersTotal, floorTier.getUses(), ceilingTier.getUses(), floorBump);
        this.efficiency = getTierStrength(tierIndex, tiersTotal, floorTier.getSpeed(), ceilingTier.getSpeed(), floorBump);
        this.attackDamage = getTierStrength(tierIndex, tiersTotal, floorTier.getAttackDamageBonus(), ceilingTier.getAttackDamageBonus(), floorBump);
    }

    @Override
    public int getUses()
    {
        return this.durability;
    }

    @Override
    public float getSpeed()
    {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus()
    {
        return this.attackDamage;
    }

    @Override
    public int getLevel()
    {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue()
    {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient()
    {
        return this.repairItem.get();
    }
}
