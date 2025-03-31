/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.MonitorBlockEntity;
import li.cil.oc2.common.network.MessageUtils;
import li.cil.oc2.common.network.Network;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record MonitorPowerMessage(BlockPos pos, boolean power) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "monitor_power");

    public MonitorPowerMessage(final MonitorBlockEntity monitor, final boolean power) {
        this(monitor.getBlockPos(), power);
    }

    public MonitorPowerMessage(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readBoolean());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeBoolean(power);
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
        MessageUtils.withNearbyServerBlockEntityForInteraction(context, pos, MonitorBlockEntity.class,
                (player, monitor) -> {
                    if (power) {
                        monitor.start();
                    } else {
                        monitor.stop();
                    }
                    Network.sendToClientsTrackingBlockEntity(new MonitorPowerMessageForwarded(monitor, power), monitor);
                });
    }
}
