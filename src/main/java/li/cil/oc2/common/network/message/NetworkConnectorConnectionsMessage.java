package li.cil.oc2.common.network.message;

import li.cil.oc2.common.network.MessageUtils;
import li.cil.oc2.common.tileentity.CableConnectorTileEntity;
import li.cil.oc2.common.tileentity.NetworkConnectorTileEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public final class NetworkConnectorConnectionsMessage {
    private BlockPos pos;
    private ArrayList<CableConnectorTileEntity.CableConnection> cableConnections;

    ///////////////////////////////////////////////////////////////////

    public NetworkConnectorConnectionsMessage(final NetworkConnectorTileEntity connector) {
        this.pos = connector.getBlockPos();
        this.cableConnections = new ArrayList<>(connector.getConnected());
    }

    public NetworkConnectorConnectionsMessage(final PacketBuffer buffer) {
        fromBytes(buffer);
    }

    ///////////////////////////////////////////////////////////////////

    public static boolean handleMessage(final NetworkConnectorConnectionsMessage message, final Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> MessageUtils.withClientTileEntityAt(message.pos, NetworkConnectorTileEntity.class,
                (tileEntity) -> tileEntity.setConnectedPositionsClient(message.cableConnections)));
        return true;
    }

    public void fromBytes(final PacketBuffer buffer) {
        pos = buffer.readBlockPos();
        cableConnections = new ArrayList<>();
        final int positionCount = buffer.readVarInt();
        for (int i = 0; i < positionCount; i++) {
            final BlockPos pos = buffer.readBlockPos();
            final float r = buffer.readFloat();
            final float g = buffer.readFloat();
            final float b = buffer.readFloat();
            cableConnections.add(new CableConnectorTileEntity.CableConnection(pos, new Vector3f(r,g,b)));
        }
    }

    public static void toBytes(final NetworkConnectorConnectionsMessage message, final PacketBuffer buffer) {
        buffer.writeBlockPos(message.pos);
        buffer.writeVarInt(message.cableConnections.size());
        for (final CableConnectorTileEntity.CableConnection conn : message.cableConnections) {
            buffer.writeBlockPos(conn.blockPos);
            buffer.writeFloat(conn.color.x());
            buffer.writeFloat(conn.color.y());
            buffer.writeFloat(conn.color.z());
        }
    }
}
