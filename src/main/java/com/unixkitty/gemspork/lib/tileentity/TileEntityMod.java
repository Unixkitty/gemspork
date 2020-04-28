package com.unixkitty.gemspork.lib.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TileEntityMod extends TileEntity
{
    public TileEntityMod(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }

    /**
     * Read saved data from disk into the tile.
     */
    @Override
    public void read(final CompoundNBT compound)
    {
        super.read(compound);
        readPacketNBT(compound);
    }

    /**
     * Write data from the tile into a compound tag for saving to disk.
     */
    @Nonnull
    @Override
    public CompoundNBT write(final CompoundNBT compound)
    {
        CompoundNBT result = super.write(compound);
        writePacketNBT(result);
        return result;
    }

    /**
     * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the
     * chunk or when many blocks change at once.
     * This compound comes back to you client-side in {@link #handleUpdateTag}
     * The default implementation ({@link TileEntity#handleUpdateTag}) calls #writeInternal)
     * which doesn't save any of our extra data so we override it to call {@link #write} instead
     */
    @Nonnull
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }

    public abstract void readPacketNBT(CompoundNBT compound);

    public abstract void writePacketNBT(CompoundNBT compound);

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        CompoundNBT compound = new CompoundNBT();
        writePacketNBT(compound);
        return new SUpdateTileEntityPacket(pos, -999, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
    {
        super.onDataPacket(net, packet);
        readPacketNBT(packet.getNbtCompound());
    }
}
