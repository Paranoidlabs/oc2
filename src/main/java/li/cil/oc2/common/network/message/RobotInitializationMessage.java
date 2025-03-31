/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.bus.CommonDeviceBusController;
import li.cil.oc2.common.entity.Robot;
import li.cil.oc2.common.network.MessageUtils;
import li.cil.oc2.common.serialization.NBTSerialization;
import li.cil.oc2.common.vm.VMRunState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record RobotInitializationMessage(int entityId, CommonDeviceBusController.BusState busState, VMRunState runState, Component bootError, CompoundTag terminal) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "robot_initialization");

    public RobotInitializationMessage(final Robot robot) {
        this(robot.getId(), robot.getVirtualMachine().getBusState(), robot.getVirtualMachine().getRunState(), robot.getVirtualMachine().getBootError(), NBTSerialization.serialize(robot.getTerminal()));
    }

    public RobotInitializationMessage(final FriendlyByteBuf buffer) {
        this(buffer.readVarInt(), buffer.readEnum(CommonDeviceBusController.BusState.class), buffer.readEnum(VMRunState.class), buffer.readComponent(), buffer.readNbt());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeVarInt(entityId);
        buffer.writeEnum(busState);
        buffer.writeEnum(runState);
        buffer.writeComponent(bootError);
        buffer.writeNbt(terminal);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {
        MessageUtils.withClientEntity(entityId, Robot.class,
            robot -> {
                robot.getVirtualMachine().setBusStateClient(busState);
                robot.getVirtualMachine().setRunStateClient(runState);
                robot.getVirtualMachine().setBootErrorClient(bootError);
                NBTSerialization.deserialize(terminal, robot.getTerminal());
            });
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {

    }
}
