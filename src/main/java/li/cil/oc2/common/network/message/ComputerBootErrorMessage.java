/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.ComputerBlockEntity;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import javax.annotation.Nullable;

public record ComputerBootErrorMessage(BlockPos pos, Component value) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "computer_boot_error");

    public ComputerBootErrorMessage(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readComponent());
    }

    public ComputerBootErrorMessage(final BlockEntity entity, final Component value) {
        this(entity.getBlockPos(), value);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeComponent(value);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void handleClientSide(PlayPayloadContext context) {
        MessageUtils.withClientBlockEntityAt(pos, ComputerBlockEntity.class,
                computer -> computer.getVirtualMachine().setBootErrorClient(value));
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {}
}
