/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.MonitorBlockEntity;
import li.cil.oc2.common.network.MessageUtils;
import li.cil.oc2.common.network.MonitorLoadBalancer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record MonitorRequestFramebufferMessage(BlockPos pos) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "monitor_request_framebuffer");

    public MonitorRequestFramebufferMessage(final MonitorBlockEntity projector) {
        this(projector.getBlockPos());
    }

    public MonitorRequestFramebufferMessage(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {
        MessageUtils.withNearbyServerBlockEntity(context, pos, MonitorBlockEntity.class,
            (player, monitor) -> MonitorLoadBalancer.updateWatcher(monitor, player));
    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {

    }
}
