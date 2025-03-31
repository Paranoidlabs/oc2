/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.client.gui.FileChooserScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;

public record ExportedFileMessage(String name, byte[] data) implements CustomMessage {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "exported_file");

    public ExportedFileMessage(final FriendlyByteBuf buffer) {
        this(buffer.readUtf(), buffer.readByteArray());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeUtf(name);
        buffer.writeByteArray(data);
    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {
        FileChooserScreen.openFileChooserForSave(name, path -> {
            try {
                Files.write(path, data);
            } catch (final IOException e) {
                LOGGER.error(e);
            }
        });
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {

    }
}
