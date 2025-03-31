/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.bus.device.rpc.item.FileImportExportCardItemDevice;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.function.Supplier;

public record ClientCanceledImportFileMessage(int file_id) implements CustomMessage {
    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "client_canceled_import_file");

    public ClientCanceledImportFileMessage(final FriendlyByteBuf buffer) {
        this(buffer.readVarInt());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeVarInt(file_id);
    }

    ///////////////////////////////////////////////////////////////////

    private void handleMessage(final PlayPayloadContext context) {
        if (context.player().isPresent()) {
            final Player player = context.player().get();
            FileImportExportCardItemDevice.cancelImport(player, file_id);
        }
    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {
        handleMessage(context);
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {
        handleMessage(context);
    }
}
