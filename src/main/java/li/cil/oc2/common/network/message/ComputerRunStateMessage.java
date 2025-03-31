/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.BlockEntities;
import li.cil.oc2.common.blockentity.ComputerBlockEntity;
import li.cil.oc2.common.network.MessageUtils;
import li.cil.oc2.common.vm.VMRunState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record ComputerRunStateMessage(BlockPos pos, VMRunState value) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "computer_run_state");

    public ComputerRunStateMessage(BlockEntity entity, VMRunState value) {
        this(entity.getBlockPos(), value);
    }

    public ComputerRunStateMessage(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readEnum(VMRunState.class));
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeEnum(value);
    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {
        MessageUtils.withClientBlockEntityAt(pos, ComputerBlockEntity.class,
                computer -> computer.getVirtualMachine().setRunStateClient(value));
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {

    }
}
