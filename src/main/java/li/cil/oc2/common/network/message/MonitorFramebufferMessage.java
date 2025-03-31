/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.MonitorBlockEntity;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.nio.ByteBuffer;

public record MonitorFramebufferMessage(BlockPos pos, ByteBuffer frame) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "monitor_framebuffer");

    public MonitorFramebufferMessage(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), ByteBuffer.allocateDirect(buffer.readVarInt()));
        buffer.readBytes(frame);
        frame.flip();
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeVarInt(frame.limit());
        buffer.writeBytes(frame);
        frame.position(0);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {
        MessageUtils.withClientBlockEntityAt(pos, MonitorBlockEntity.class,
                monitor -> monitor.applyNextFrameClient(frame));
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {

    }
}
