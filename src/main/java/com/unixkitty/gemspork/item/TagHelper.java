package com.unixkitty.gemspork.item;

import com.unixkitty.gemspork.lib.HelperUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public final class TagHelper
{
    public static ResourceLocation modResource(String modId, String type, String name)
    {
        return new ResourceLocation(modId, type + "/" + name);
    }

    public static ITag.INamedTag<Item> itemTag(String modId, String name)
    {
        return ItemTags.bind(HelperUtil.prefixResource(modId, name).toString());
    }

    public static ITag.INamedTag<Block> blockTag(String modId, String name)
    {
        return BlockTags.bind(HelperUtil.prefixResource(modId, name).toString());
    }

    public static ITag.INamedTag<Block> forgeBlockTag(String type, String name)
    {
        return BlockTags.bind(modResource("forge", type, name).toString());
    }

    public static ITag.INamedTag<Item> forgeItemTag(String type, String name)
    {
        return ItemTags.bind(modResource("forge", type, name).toString());
    }
}
