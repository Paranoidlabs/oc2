/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.NetworkConnectorBlockEntity;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.ArrayList;

public record NetworkConnectorConnectionsMessage(BlockPos pos, ArrayList<BlockPos> connectedPositions) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "network_connector_connections");
    ///////////////////////////////////////////////////////////////////

    public NetworkConnectorConnectionsMessage(final NetworkConnectorBlockEntity networkConnector) {
        this(networkConnector.getBlockPos(), new ArrayList<>(networkConnector.getConnectedPositions()));
    }

    public NetworkConnectorConnectionsMessage(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readVarInt(), buffer);
    }

    public NetworkConnectorConnectionsMessage(final BlockPos pos, final int connectedCount, final FriendlyByteBuf buffer) {
        this(pos, new ArrayList<>(connectedCount));
        for (int i = 0; i < connectedCount; i++) {
            final BlockPos p = buffer.readBlockPos();
            connectedPositions.add(p);
        }
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeVarInt(connectedPositions.size());
        for (final BlockPos pos : connectedPositions) {
            buffer.writeBlockPos(pos);
        }
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {

    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {
        MessageUtils.withClientBlockEntityAt(pos, NetworkConnectorBlockEntity.class,
                networkConnector -> networkConnector.setConnectedPositionsClient(connectedPositions));
    }
}
