/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.KeyboardBlockEntity;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record KeyboardInputMessage(BlockPos pos, int keycode, boolean isDown) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "keyboard_input");

    public KeyboardInputMessage(final KeyboardBlockEntity keyboard, final int keycode, final boolean isDown) {
        this(keyboard.getBlockPos(), keycode, isDown);
    }

    public KeyboardInputMessage(final FriendlyByteBuf buffer) {
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
        MessageUtils.withNearbyServerBlockEntityForInteraction(context, pos, KeyboardBlockEntity.class,
                (player, keyboard) -> keyboard.handleInput(keycode, isDown));
    }
}
