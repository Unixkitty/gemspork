package com.unixkitty.gemspork.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.SoundEvent;

public class DynamicTieredArmorProperties extends DynamicTierProperties implements IArmorMaterial
{
    private final ArmorMaterial FLOOR_TIER;
    private final ArmorMaterial CEILING_TIER;

    private final String name;

    private final int tierIndex;
    private final int tiersTotal;
    private final float floorBump;

    private final float toughness;
    private final int enchantability;

    private final SoundEvent equipSound;

    public DynamicTieredArmorProperties(String name, int tierIndex, int tiersTotal, int enchantability, float floorBump, ArmorMaterial floorTier, ArmorMaterial ceilingTier, SoundEvent equipSound, Tag<Item> repairItem)
    {
        super(repairItem);

        this.FLOOR_TIER = floorTier;
        this.CEILING_TIER = ceilingTier;

        this.name = name;

        this.tierIndex = tierIndex;
        this.tiersTotal = tiersTotal;
        this.floorBump = floorBump;

        this.toughness = getTierStrength(tierIndex, tiersTotal, FLOOR_TIER.getToughness(), this.CEILING_TIER.getToughness(), floorBump);
        this.enchantability = calcEnchantability(tierIndex, tiersTotal, floorTier.getEnchantability(), ceilingTier.getEnchantability(), floorBump, enchantability);

        this.equipSound = equipSound;
    }

    /* Interface methods begin */

    @Override
    public int getDurability(EquipmentSlotType slot)
    {
        return (int) getTierStrength(this.tierIndex, this.tiersTotal, this.FLOOR_TIER.getDurability(slot), this.CEILING_TIER.getDurability(slot), this.floorBump);
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slot)
    {
        return (int) getTierStrength(this.tierIndex, this.tiersTotal, this.FLOOR_TIER.getDamageReductionAmount(slot), this.CEILING_TIER.getDamageReductionAmount(slot), this.floorBump);
    }

    @Override
    public int getEnchantability()
    {
        return this.enchantability;
    }

    @Override
    public SoundEvent getSoundEvent()
    {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairMaterial()
    {
        return this.repairItem.getValue();
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public float getToughness()
    {
        return this.toughness;
    }
}