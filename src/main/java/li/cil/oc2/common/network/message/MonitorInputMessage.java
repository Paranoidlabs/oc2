/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.MonitorBlockEntity;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record MonitorInputMessage(BlockPos pos, int keycode, boolean isDown) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "monitor_input");

    public MonitorInputMessage(final MonitorBlockEntity keyboard, final int keycode, final boolean isDown) {
        this(keyboard.getBlockPos(), keycode, isDown);
    }

    public MonitorInputMessage(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readVarInt(), buffer.readBoolean());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeVarInt(keycode);
        buffer.writeBoolean(isDown);
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
                (player, monitor) -> monitor.handleInput(keycode, isDown));
    }
}
