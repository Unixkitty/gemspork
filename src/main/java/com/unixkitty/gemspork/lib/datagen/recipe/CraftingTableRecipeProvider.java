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

        ShapedRecipeBuilder.shaped(output)
                .define('I', Ingredient.of(ingredient))
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .unlockedBy("has_item", has(ingredient))
                .save(consumer);

        //Decompression
        IItemProvider output2 = HelperUtil.itemFromTag(MODID, ingredient);
        ShapelessRecipeBuilder.shapeless(output2, 9)
                .unlockedBy("has_item", has(output2))
                .requires(output)
                .save(consumer);
    }

    protected void registerToolSetRecipes(Consumer<IFinishedRecipe> consumer, ITag.INamedTag<Item> ingredient)
    {
        ICriterionInstance criterion = has(ingredient);

        Item axe = HelperUtil.itemFromMaterialTag(ingredient, MODID, "axe");
        Item sword = HelperUtil.itemFromMaterialTag(ingredient, MODID, "sword");
        Item shovel = HelperUtil.itemFromMaterialTag(ingredient, MODID, "shovel");
        Item pickaxe = HelperUtil.itemFromMaterialTag(ingredient, MODID, "pickaxe");
        Item hoe = HelperUtil.itemFromMaterialTag(ingredient, MODID, "hoe");

        ShapedRecipeBuilder.shaped(pickaxe)
                .define('S', ingredient)
                .define('T', Tags.Items.RODS_WOODEN)
                .pattern("SSS")
                .pattern(" T ")
                .pattern(" T ")
                .unlockedBy("has_item", criterion)
                .save(consumer);
        ShapedRecipeBuilder.shaped(shovel)
                .define('S', ingredient)
                .define('T', Tags.Items.RODS_WOODEN)
                .pattern("S")
                .pattern("T")
                .pattern("T")
                .unlockedBy("has_item", criterion)
                .save(consumer);
        ShapedRecipeBuilder.shaped(axe)
                .define('S', ingredient)
                .define('T', Tags.Items.RODS_WOODEN)
                .pattern("SS")
                .pattern("TS")
                .pattern("T ")
                .unlockedBy("has_item", criterion)
                .save(consumer);
        ShapedRecipeBuilder.shaped(sword)
                .define('S', ingredient)
                .define('T', Tags.Items.RODS_WOODEN)
                .pattern("S")
                .pattern("S")
                .pattern("T")
                .unlockedBy("has_item", criterion)
                .save(consumer);
        ShapedRecipeBuilder.shaped(hoe)
                .define('S', ingredient)
                .define('T', Tags.Items.RODS_WOODEN)
                .pattern("SS")
                .pattern(" T")
                .pattern(" T")
                .unlockedBy("has_item", criterion)
                .save(consumer);
    }

    protected void registerSimpleArmorSet(Consumer<IFinishedRecipe> consumer, ITag.INamedTag<Item> ingredient)
    {
        ICriterionInstance criterion = has(ingredient);

        Item helmet = HelperUtil.armorItemFromMaterialResource(ingredient, EquipmentSlotType.HEAD, MODID);
        Item chestplate = HelperUtil.armorItemFromMaterialResource(ingredient, EquipmentSlotType.CHEST, MODID);
        Item leggings = HelperUtil.armorItemFromMaterialResource(ingredient, EquipmentSlotType.LEGS, MODID);
        Item boots = HelperUtil.armorItemFromMaterialResource(ingredient, EquipmentSlotType.FEET, MODID);

        ShapedRecipeBuilder.shaped(helmet)
                .define('S', ingredient)
                .pattern("SSS")
                .pattern("S S")
                .unlockedBy("has_item", criterion)
                .save(consumer);
        ShapedRecipeBuilder.shaped(chestplate)
                .define('S', ingredient)
                .pattern("S S")
                .pattern("SSS")
                .pattern("SSS")
                .unlockedBy("has_item", criterion)
                .save(consumer);
        ShapedRecipeBuilder.shaped(leggings)
                .define('S', ingredient)
                .pattern("SSS")
                .pattern("S S")
                .pattern("S S")
                .unlockedBy("has_item", criterion)
                .save(consumer);
        ShapedRecipeBuilder.shaped(boots)
                .define('S', ingredient)
                .pattern("S S")
                .pattern("S S")
                .unlockedBy("has_item", criterion)
                .save(consumer);
    }

    @Override
    public String getName()
    {
        return MODID + " " + this.getClass().getSimpleName();
    }
}
