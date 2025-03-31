package li.cil.oc2.common.network;

import li.cil.oc2.common.blockentity.BusCableBlockEntity;
import li.cil.oc2.common.network.message.BusCableFacadeMessage;
import li.cil.oc2.common.network.message.CustomMessage;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ClientPayloadHandler {
    private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();
    public static ClientPayloadHandler getInstance() {
        return INSTANCE;
    }

    public <T extends CustomMessage> void handle(final T message, final PlayPayloadContext context) {
        message.handleClientSide(context);
    }
}
