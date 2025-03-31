/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.ProjectorBlockEntity;
import li.cil.oc2.common.network.MessageUtils;
import li.cil.oc2.common.network.ProjectorLoadBalancer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record ProjectorRequestFramebufferMessage(BlockPos pos) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "projector_request_framebuffer");

    public ProjectorRequestFramebufferMessage(final ProjectorBlockEntity projector) {
        this(projector.getBlockPos());
    }

    public ProjectorRequestFramebufferMessage(final FriendlyByteBuf buffer) {
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
    public void handleClientSide(PlayPayloadContext context) {

    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {
        MessageUtils.withNearbyServerBlockEntity(context, pos, ProjectorBlockEntity.class,
                (player, projector) -> ProjectorLoadBalancer.updateWatcher(projector, player));
    }
}
