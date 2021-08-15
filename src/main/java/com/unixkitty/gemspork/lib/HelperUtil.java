package com.unixkitty.gemspork.lib;

import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;

//TODO clean up these methods if possible
public final class HelperUtil
{
    public static ResourceLocation prefixResource(String domain, String path)
    {
        return new ResourceLocation(domain, path);
    }

    public static boolean isResource(@Nullable ResourceLocation resourceLocation, String resource, boolean exact)
    {
        return resourceLocation != null && (exact ? resourceLocation.getPath().matches(resource) : resourceLocation.getPath().startsWith(resource));
    }

    public static IItemProvider itemFromTag(String resourceDomain, ITag.INamedTag<Item> ingredient)
    {
        return ForgeRegistries.ITEMS.getValue(prefixResource(resourceDomain, materialString(ingredient)));
    }

    public static Item itemFromMaterialTag(ITag.INamedTag<Item> material, String resourceDomain, String type)
    {
        return ForgeRegistries.ITEMS.getValue(HelperUtil.materialResource(material, resourceDomain, type));
    }

    public static Item armorItemFromMaterialResource(ITag.INamedTag<Item> material, EquipmentSlotType slot, String resourceDomain)
    {
        return ForgeRegistries.ITEMS.getValue(armorResource(material, slot, resourceDomain));
    }

    public static ResourceLocation materialResource(ITag.INamedTag<Item> material, String resourceDomain, String type)
    {
        return prefixResource(resourceDomain, materialString(material) + "_" + type);
    }

    public static ResourceLocation armorResource(ITag.INamedTag<Item> material, EquipmentSlotType slot, String resourceDomain)
    {
        return prefixResource(resourceDomain, armorMaterialString(material, slot));
    }

    public static String armorMaterialString(String material, EquipmentSlotType slot)
    {
        return material + "_" + armorSlotString(slot);
    }

    public static String armorMaterialString(ITag.INamedTag<Item> material, EquipmentSlotType slot)
    {
        return materialString(material) + "_" + armorSlotString(slot);
    }

    public static String materialString(ITag.INamedTag<Item> ingredient)
    {
        String material = ingredient.getName().getPath();

        if (material.contains("/"))
        {
            material = StringUtils.reverse(StringUtils.reverse(material).split("/")[0]);
        }

        return material;
    }

    public static String armorSlotString(EquipmentSlotType slot)
    {
        switch (slot)
        {
            case FEET:
                return "boots";
            case HEAD:
                return "helmet";
            case LEGS:
                return "leggings";
            case CHEST:
                return "chestplate";
            default:
                return "";
        }
    }

    public static ConfiguredFeature<?, ?> registerOreFeature(RuleTest replaceBlock, Block ore, int veinSize, int timesPerChunk, int minHeight, int maxHeight)
    {
        return Feature.ORE.configured(
                new OreFeatureConfig(
                        replaceBlock,
                        ore.defaultBlockState(),
                        veinSize
                )
        ).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(
                        minHeight,
                        0,
                        maxHeight
                )).squared().count(timesPerChunk)
        );
    }

    /*public static void addOreToBiome(Biome biome, OreFeatureConfig.FillerBlockType replaceBlock, Block ore, boolean enabled, int veinSize, int timesPerChunk, int minHeight, int maxHeight)
    {
        if (!enabled) return;

        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(
                        replaceBlock,
                        ore.getDefaultState(),
                        veinSize
                ))
                        .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(timesPerChunk, minHeight, 0, maxHeight)))
        );

    }*/
}
