package com.unixkitty.gemspork.lib.datagen.recipe;

import com.unixkitty.gemspork.lib.HelperUtil;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;

import java.util.function.Consumer;

public abstract class SmeltingRecipeProvider extends RecipeProvider
{
    private final String MODID;

    public SmeltingRecipeProvider(String modId, DataGenerator generatorIn)
    {
        super(generatorIn);

        this.MODID = modId;
    }

    protected void addBasicOreCooking(Consumer<IFinishedRecipe> consumer, IItemProvider input, IItemProvider result, String name)
    {
        CookingRecipeBuilder.smelting(
                        Ingredient.of(input),
                        result,
                        1.0F,
                        200)
                .unlockedBy("has_" + name + "_ore", has(input))
                .save(consumer, HelperUtil.prefixResource(MODID, "smelting/" + name + "_from_smelting"));
    }

    @Override
    public String getName()
    {
        return MODID + " " + this.getClass().getSimpleName();
    }
}
