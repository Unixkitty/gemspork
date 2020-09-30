package com.unixkitty.gemspork.lib.datagen.recipe;

import com.unixkitty.gemspork.lib.HelperUtil;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.data.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class CraftingTableRecipeProvider extends RecipeProvider
{
    private final String MODID;

    public CraftingTableRecipeProvider(String modId, DataGenerator generatorIn)
    {
        super(generatorIn);

        this.MODID = modId;
    }

    protected void registerCompression(Consumer<IFinishedRecipe> consumer, ITag.INamedTag<Item> ingredient)
    {
        IItemProvider output = HelperUtil.itemFromMaterialTag(ingredient, MODID, "block");

        ShapedRecipeBuilder.shapedRecipe(output)
                .key('I', Ingredient.fromTag(ingredient))
                .patternLine("III")
                .patternLine("III")
                .patternLine("III")
                .addCriterion("has_item", hasItem(ingredient))
                .build(consumer);

        //Decompression
        IItemProvider output2 = HelperUtil.itemFromTag(MODID, ingredient);
        ShapelessRecipeBuilder.shapelessRecipe(output2, 9)
                .addCriterion("has_item", hasItem(output2))
                .addIngredient(output)
                .build(consumer);
    }

    protected void registerToolSetRecipes(Consumer<IFinishedRecipe> consumer, ITag.INamedTag<Item> ingredient)
    {
        ICriterionInstance criterion = hasItem(ingredient);

        Item axe = HelperUtil.itemFromMaterialTag(ingredient, MODID, "axe");
        Item sword = HelperUtil.itemFromMaterialTag(ingredient, MODID, "sword");
        Item shovel = HelperUtil.itemFromMaterialTag(ingredient, MODID, "shovel");
        Item pickaxe = HelperUtil.itemFromMaterialTag(ingredient, MODID, "pickaxe");
        Item hoe = HelperUtil.itemFromMaterialTag(ingredient, MODID, "hoe");

        ShapedRecipeBuilder.shapedRecipe(pickaxe)
                .key('S', ingredient)
                .key('T', Tags.Items.RODS_WOODEN)
                .patternLine("SSS")
                .patternLine(" T ")
                .patternLine(" T ")
                .addCriterion("has_item", criterion)
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(shovel)
                .key('S', ingredient)
                .key('T', Tags.Items.RODS_WOODEN)
                .patternLine("S")
                .patternLine("T")
                .patternLine("T")
                .addCriterion("has_item", criterion)
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(axe)
                .key('S', ingredient)
                .key('T', Tags.Items.RODS_WOODEN)
                .patternLine("SS")
                .patternLine("TS")
                .patternLine("T ")
                .addCriterion("has_item", criterion)
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(sword)
                .key('S', ingredient)
                .key('T', Tags.Items.RODS_WOODEN)
                .patternLine("S")
                .patternLine("S")
                .patternLine("T")
                .addCriterion("has_item", criterion)
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(hoe)
                .key('S', ingredient)
                .key('T', Tags.Items.RODS_WOODEN)
                .patternLine("SS")
                .patternLine(" T")
                .patternLine(" T")
                .addCriterion("has_item", criterion)
                .build(consumer);
    }

    protected void registerSimpleArmorSet(Consumer<IFinishedRecipe> consumer, ITag.INamedTag<Item> ingredient)
    {
        ICriterionInstance criterion = hasItem(ingredient);

        Item helmet = HelperUtil.armorItemFromMaterialResource(ingredient, EquipmentSlotType.HEAD, MODID);
        Item chestplate = HelperUtil.armorItemFromMaterialResource(ingredient, EquipmentSlotType.CHEST, MODID);
        Item leggings = HelperUtil.armorItemFromMaterialResource(ingredient, EquipmentSlotType.LEGS, MODID);
        Item boots = HelperUtil.armorItemFromMaterialResource(ingredient, EquipmentSlotType.FEET, MODID);

        ShapedRecipeBuilder.shapedRecipe(helmet)
                .key('S', ingredient)
                .patternLine("SSS")
                .patternLine("S S")
                .addCriterion("has_item", criterion)
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(chestplate)
                .key('S', ingredient)
                .patternLine("S S")
                .patternLine("SSS")
                .patternLine("SSS")
                .addCriterion("has_item", criterion)
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(leggings)
                .key('S', ingredient)
                .patternLine("SSS")
                .patternLine("S S")
                .patternLine("S S")
                .addCriterion("has_item", criterion)
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(boots)
                .key('S', ingredient)
                .patternLine("S S")
                .patternLine("S S")
                .addCriterion("has_item", criterion)
                .build(consumer);
    }

    @Override
    public String getName()
    {
        return MODID + " " + this.getClass().getSimpleName();
    }
}
