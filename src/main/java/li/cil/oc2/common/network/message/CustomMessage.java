package li.cil.oc2.common.network.message;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public interface CustomMessage extends CustomPacketPayload {
    void handleClientSide(final PlayPayloadContext context);
    void handleServerSide(final PlayPayloadContext context);
}
