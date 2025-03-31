/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.entity.Robot;
import li.cil.oc2.common.network.MessageUtils;
import li.cil.oc2.common.vm.VMRunState;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record RobotRunStateMessage(int entityId, VMRunState value) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "robot_run_state");

    public RobotRunStateMessage(final Robot robot, final VMRunState value) {
        this(robot.getId(), value);
    }

    public RobotRunStateMessage(final FriendlyByteBuf buffer) {
        this(buffer.readVarInt(), buffer.readEnum(VMRunState.class));
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
            robot -> robot.getVirtualMachine().setRunStateClient(value));
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {

    }
}
