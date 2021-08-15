package com.unixkitty.gemspork.item;

import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;

public class ItemNBTHelper
{
    /**
     * Serializes the given stack such that {@link net.minecraftforge.common.crafting.CraftingHelper#getItemStack}
     * would be able to read the result back
     */
    public static JsonObject serializeStack(ItemStack stack)
    {
        CompoundNBT nbt = stack.save(new CompoundNBT());
        byte c = nbt.getByte("Count");
        if (c != 1)
        {
            nbt.putByte("count", c);
        }
        nbt.remove("Count");
        renameTag(nbt, "id", "item");
        renameTag(nbt, "tag", "nbt");
        Dynamic<INBT> dyn = new Dynamic<>(NBTDynamicOps.INSTANCE, nbt);
        return dyn.convert(JsonOps.INSTANCE).getValue().getAsJsonObject();
    }

    public static void renameTag(CompoundNBT nbt, String oldName, String newName)
    {
        INBT tag = nbt.get(oldName);
        if (tag != null)
        {
            nbt.remove(oldName);
            nbt.put(newName, tag);
        }
    }
}
