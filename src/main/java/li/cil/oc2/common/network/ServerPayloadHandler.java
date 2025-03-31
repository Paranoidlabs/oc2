package li.cil.oc2.common.network;

import li.cil.oc2.common.network.message.BusCableFacadeMessage;
import li.cil.oc2.common.network.message.CustomMessage;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ServerPayloadHandler {
    private static final ServerPayloadHandler INSTANCE = new ServerPayloadHandler();
    public static ServerPayloadHandler getInstance() {
        return INSTANCE;
    }

    public <T extends CustomMessage> void handle(final T message, final PlayPayloadContext context) {
        message.handleServerSide(context);
    }
}
