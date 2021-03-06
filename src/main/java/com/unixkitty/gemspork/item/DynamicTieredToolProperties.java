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

        this.harvestLevel = harvestLevel == -1 ? (tiersTotal == (tierIndex + 1) ? ceilingTier.getHarvestLevel() : floorTier.getHarvestLevel()) : harvestLevel;
        this.enchantability = calcEnchantability(tierIndex, tiersTotal, floorTier.getEnchantability(), ceilingTier.getEnchantability(), floorBump, enchantability);

        this.durability = getTierStrengthAsInt(tierIndex, tiersTotal, floorTier.getMaxUses(), ceilingTier.getMaxUses(), floorBump);
        this.efficiency = getTierStrength(tierIndex, tiersTotal, floorTier.getEfficiency(), ceilingTier.getEfficiency(), floorBump);
        this.attackDamage = getTierStrength(tierIndex, tiersTotal, floorTier.getAttackDamage(), ceilingTier.getAttackDamage(), floorBump);
    }

    @Override
    public int getMaxUses()
    {
        return this.durability;
    }

    @Override
    public float getEfficiency()
    {
        return this.efficiency;
    }

    @Override
    public float getAttackDamage()
    {
        return this.attackDamage;
    }

    @Override
    public int getHarvestLevel()
    {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantability()
    {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairMaterial()
    {
        return this.repairItem.getValue();
    }
}
