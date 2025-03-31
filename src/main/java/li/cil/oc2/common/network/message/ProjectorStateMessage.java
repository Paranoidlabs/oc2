/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.ProjectorBlockEntity;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record ProjectorStateMessage(BlockPos pos, boolean isMounted, boolean hasEnergy) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "projector_state");

    public ProjectorStateMessage(final ProjectorBlockEntity projector, final boolean isMounted, final boolean hasEnergy) {
        this(projector.getBlockPos(), isMounted, hasEnergy);
    }

    public ProjectorStateMessage(final FriendlyByteBuf buffer) {
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
    public void handleClientSide(PlayPayloadContext context) {
        MessageUtils.withClientBlockEntityAt(pos, ProjectorBlockEntity.class,
            projector -> projector.applyProjectorStateClient(isMounted, hasEnergy));
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {

    }
}
