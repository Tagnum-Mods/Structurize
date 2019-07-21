package com.ldtteam.structurize.network.messages;

import com.ldtteam.structurize.items.ItemScanTool;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Send the scan message for a player to the server.
 */
public class ScanOnServerMessage implements IMessage
{
    /**
     * Position to scan from.
     */
    private BlockPos from;

    /**
     * Position to scan to.
     */
    private BlockPos to;

    /**
     * Name of the file.
     */
    private String name;

    /**
     * Empty public constructor.
     */
    public ScanOnServerMessage()
    {
        super();
    }


    public ScanOnServerMessage(@NotNull final BlockPos from, @NotNull final BlockPos to, @NotNull final String name)
    {
        super();
        this.from = from;
        this.to = to;
        this.name = name;
    }

    @Override
    public void fromBytes(@NotNull final PacketBuffer buf)
    {
        name = buf.readString();
        from = buf.readBlockPos();
        to = buf.readBlockPos();
    }

    @Override
    public void toBytes(@NotNull final PacketBuffer buf)
    {
        buf.writeString(name);
        buf.writeBlockPos(from);
        buf.writeBlockPos(to);
    }

    @Nullable
    @Override
    public LogicalSide getExecutionSide()
    {
        return LogicalSide.SERVER;
    }

    @Override
    public void onExecute(final NetworkEvent.Context ctxIn, final boolean isLogicalServer)
    {
        ItemScanTool.saveStructure(ctxIn.getSender().getEntityWorld(), from, to, ctxIn.getSender(), name);
    }
}
