/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.container.NetworkTunnelContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record NetworkTunnelLinkMessage(int containerId) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "network_tunnel_link");

    public NetworkTunnelLinkMessage(final FriendlyByteBuf buffer) {
        this(buffer.readVarInt());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeVarInt(containerId);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {

    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {
        if (context.player().isEmpty()) {
            return;
        }
        final Player player = context.player().get();

        final AbstractContainerMenu container = player.containerMenu;
        if (container.containerId != containerId) {
            return;
        }

        if (container instanceof NetworkTunnelContainer networkTunnelContainer) {
            networkTunnelContainer.createTunnel();
        }
    }
}
