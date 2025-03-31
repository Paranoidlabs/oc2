/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.client.gui.FileChooserScreen;
import li.cil.oc2.common.bus.device.rpc.item.FileImportExportCardItemDevice;
import li.cil.oc2.common.network.Network;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static li.cil.oc2.common.util.TranslationUtils.text;

public record RequestImportedFileMessage(int file_id) implements CustomMessage {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final MutableComponent FILE_TOO_LARGE_TEXT = text("message.{mod}.import_file.file_too_large");

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "request_imported_file");

    public RequestImportedFileMessage(final FriendlyByteBuf buffer) {
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
        FileChooserScreen.openFileChooserForLoad(new FileChooserScreen.FileChooserCallback() {
            @Override
            public void onFileSelected(final Path path) {
                try {
                    final String fileName = path.getFileName().toString();
                    final byte[] data = Files.readAllBytes(path);
                    if (data.length > FileImportExportCardItemDevice.MAX_TRANSFERRED_FILE_SIZE) {
                        Network.sendToServer(new ClientCanceledImportFileMessage(file_id));
                        Minecraft.getInstance().gui.getChat().addMessage(FILE_TOO_LARGE_TEXT
                            .withStyle(s -> s.withColor(TextColor.fromRgb(0xFFA0A0))));
                    } else {
                        //MultipartMessage.sendToServer(new ImportedFileMessage(file_id, fileName, data));
                    }
                } catch (final IOException e) {
                    LOGGER.error(e);
                }
            }

            @Override
            public void onCanceled() {
                Network.sendToServer(new ClientCanceledImportFileMessage(file_id));
            }
        });
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {

    }
}
