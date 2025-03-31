/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.bus.CommonDeviceBusController;
import li.cil.oc2.common.entity.Robot;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record RobotBusStateMessage(int entityId, CommonDeviceBusController.BusState value) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "robot_bus_state");

    public RobotBusStateMessage(final Robot robot, final CommonDeviceBusController.BusState value) {
        this(robot.getId(), value);
    }

    public RobotBusStateMessage(final FriendlyByteBuf buffer) {
        this(buffer.readVarInt(), buffer.readEnum(CommonDeviceBusController.BusState.class));
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeVarInt(entityId);
        buffer.writeEnum(value);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {
        MessageUtils.withClientEntity(entityId, Robot.class,
            robot -> robot.getVirtualMachine().setBusStateClient(value));
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {

    }
}
