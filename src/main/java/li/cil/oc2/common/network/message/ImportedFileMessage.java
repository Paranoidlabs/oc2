/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.bus.device.rpc.item.FileImportExportCardItemDevice;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record ImportedFileMessage(int file_id, String name, byte[] data) implements CustomMessage {
    private static final int MAX_NAME_LENGTH = 256;

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "imported_file");

    public ImportedFileMessage(final FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readUtf(MAX_NAME_LENGTH), buffer.readByteArray());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeVarInt(file_id);
        buffer.writeUtf(name, MAX_NAME_LENGTH);
        buffer.writeByteArray(data);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {
        FileImportExportCardItemDevice.setImportedFile(file_id, name, data);
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {
        FileImportExportCardItemDevice.setImportedFile(file_id, name, data);
    }
}
