package com.unixkitty.gemspork.lib.datagen.loot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

//Thanks Botania/src/main/java/vazkii/botania/data/BlockLootProvider.java
public class BlockLootProvider implements IDataProvider
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator generator;
    private final Map<Block, Function<Block, LootTable.Builder>> functionTable = new HashMap<>();

    private final String MODID;

    public BlockLootProvider(String modId, DataGenerator generator)
    {
        this.generator = generator;
        this.MODID = modId;

        //addTables();
    }

    protected void registerLoot(Block block, Function<Block, LootTable.Builder> table)
    {
        functionTable.put(block, table);
    }

    @Override
    public final void run(DirectoryCache cache) throws IOException
    {
        Map<ResourceLocation, LootTable.Builder> tables = new HashMap<>();

        for (Block b : ForgeRegistries.BLOCKS)
        {
            if (!MODID.equals(Objects.requireNonNull(b.getRegistryName()).getNamespace()))
            {
                continue;
            }
            Function<Block, LootTable.Builder> func = functionTable.getOrDefault(b, BlockLootProvider::genRegular);
            tables.put(b.getRegistryName(), func.apply(b));
        }

        for (Map.Entry<ResourceLocation, LootTable.Builder> e : tables.entrySet())
        {
            Path path = getPath(generator.getOutputFolder(), e.getKey());
            IDataProvider.save(GSON, cache, LootTableManager.serialize(e.getValue().setParamSet(LootParameterSets.BLOCK).build()), path);
        }
    }

    private static Path getPath(Path root, ResourceLocation resourceLocation)
    {
        return root.resolve("data/" + resourceLocation.getNamespace() + "/loot_tables/blocks/" + resourceLocation.getPath() + ".json");
    }

    protected static LootTable.Builder genEmpty(Block block)
    {
        return LootTable.lootTable();
    }

    protected static LootTable.Builder genCopyNbt(Block block, String... tags)
    {
        LootEntry.Builder<?> entry = ItemLootEntry.lootTableItem(block);
        CopyNbt.Builder func = CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY);

        for (String tag : tags)
        {
            func = func.copy(tag, "BlockEntityTag." + tag);
        }

        LootPool.Builder pool = LootPool.lootPool().name("main").setRolls(ConstantRange.exactly(1)).add(entry).when(SurvivesExplosion.survivesExplosion()).apply(func);

        return LootTable.lootTable().withPool(pool);
    }

    protected static LootTable.Builder genSilkTouchableWithFortune(Block block, IItemProvider item, boolean applyFortune, int baseCount)
    {
        ItemPredicate.Builder silkPred = ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)));
        LootEntry.Builder<?> silk = ItemLootEntry.lootTableItem(block).when(MatchTool.toolMatches(silkPred));
        StandaloneLootEntry.Builder<?> nonSilk = ItemLootEntry.lootTableItem(item != null ? item : block);

        if (baseCount != 1)
        {
            nonSilk.apply(SetCount.setCount(ConstantRange.exactly(baseCount)));
        }

        if (applyFortune)
        {
            nonSilk.apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE));
        }

        nonSilk.apply(ExplosionDecay.explosionDecay());

        LootEntry.Builder<?> entry = AlternativesLootEntry.alternatives(silk, nonSilk);
        LootPool.Builder pool = LootPool.lootPool().name("main").setRolls(ConstantRange.exactly(1)).add(entry);

        return LootTable.lootTable().withPool(pool);
    }

    protected static LootTable.Builder genRegular(Block block)
    {
        LootEntry.Builder<?> entry = ItemLootEntry.lootTableItem(block);
        LootPool.Builder pool = LootPool.lootPool().name("main").setRolls(ConstantRange.exactly(1)).add(entry).when(SurvivesExplosion.survivesExplosion());

        return LootTable.lootTable().withPool(pool);
    }

    @Override
    public String getName()
    {
        return MODID + " " + this.getClass().getSimpleName();
    }
}

