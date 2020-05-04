package com.unixkitty.gemspork.lib.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ItemTagProvider extends ItemTagsProvider
{
    private final String MODID;
    private Set<ResourceLocation> filter = null;

    public ItemTagProvider(String modId, DataGenerator generatorIn)
    {
        super(generatorIn);

        this.MODID = modId;
    }

    @Override
    protected void registerTags()
    {
        super.registerTags();

        filter = this.tagToBuilder.keySet().stream().map(Tag::getId).collect(Collectors.toSet());

        registerCustomTags();
    }

    protected abstract void registerCustomTags();

    @Override
    protected Path makePath(ResourceLocation id)
    {
        return filter != null && filter.contains(id) ? null : super.makePath(id); //We don't want to save vanilla tags.
    }

    @Override
    public String getName()
    {
        return this.MODID + " " + this.getClass().getSimpleName();
    }
}
