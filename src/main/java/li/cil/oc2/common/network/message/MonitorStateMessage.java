/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.MonitorBlockEntity;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record MonitorStateMessage(BlockPos pos, boolean isMounted, boolean hasEnergy) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "monitor_state");

    public MonitorStateMessage(final MonitorBlockEntity monitor, final boolean isMounted, final boolean hasEnergy) {
        this(monitor.getBlockPos(), isMounted, hasEnergy);
    }

    public MonitorStateMessage(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readBoolean(), buffer.readBoolean());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeBoolean(isMounted);
        buffer.writeBoolean(hasEnergy);
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
        MessageUtils.withClientBlockEntityAt(pos, MonitorBlockEntity.class,
            monitor -> monitor.applyMonitorStateClient(isMounted, hasEnergy));
    }
}
