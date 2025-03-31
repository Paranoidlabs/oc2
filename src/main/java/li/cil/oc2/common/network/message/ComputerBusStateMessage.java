/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.ComputerBlockEntity;
import li.cil.oc2.common.bus.CommonDeviceBusController;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record ComputerBusStateMessage(BlockPos pos, CommonDeviceBusController.BusState value) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "computer_bus_state");

    public ComputerBusStateMessage(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readEnum(CommonDeviceBusController.BusState.class));
    }

    public ComputerBusStateMessage(final BlockEntity entity, final CommonDeviceBusController.BusState value) {
        this(entity.getBlockPos(), value);
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
                computer -> computer.getVirtualMachine().setBusStateClient(value));
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {

    }
}
