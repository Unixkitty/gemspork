package com.unixkitty.gemspork.item;

import com.unixkitty.gemspork.lib.HelperUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public final class TagHelper
{
    public static ResourceLocation modResource(String modId, String type, String name)
    {
        return new ResourceLocation(modId, type + "/" + name);
    }

    public static Tag<Item> itemTag(String modId, String name)
    {
        return new ItemTags.Wrapper(HelperUtil.prefixResource(modId, name));
    }

    public static Tag<Block> blockTag(String modId, String name)
    {
        return new BlockTags.Wrapper(HelperUtil.prefixResource(modId, name));
    }

    public static Tag<Block> forgeBlockTag(String type, String name)
    {
        return new BlockTags.Wrapper(modResource("forge", type, name));
    }

    public static Tag<Item> forgeItemTag(String type, String name)
    {
        return new ItemTags.Wrapper(modResource("forge", type, name));
    }
}
