/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.bus.device.rpc.item.FileImportExportCardItemDevice;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record ServerCanceledImportFileMessage(int file_id) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "server_canceled_import_file");

    public ServerCanceledImportFileMessage(final FriendlyByteBuf buffer) {
        this(buffer.readVarInt());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeVarInt(file_id);
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
        if (context.player().isPresent()) {
            final Player player = context.player().get();
            FileImportExportCardItemDevice.cancelImport(player, file_id);
        }
    }
}
