/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.entity.Robot;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import javax.annotation.Nullable;

public record RobotBootErrorMessage(int entityId, Component value) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "robot_boot_error");

    public RobotBootErrorMessage(final Robot robot, @Nullable final Component value) {
        this(robot.getId(), value);
    }

    public RobotBootErrorMessage(final FriendlyByteBuf buffer) {
        this(buffer.readVarInt(), buffer.readComponent());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeVarInt(entityId);
        buffer.writeComponent(value);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {
        MessageUtils.withClientEntity(entityId, Robot.class,
            robot -> robot.getVirtualMachine().setBootErrorClient(value));
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {

    }
}
