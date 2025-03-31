/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.entity.Robot;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record RobotPowerMessage(int entityId, boolean power) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "robot_power");

    public RobotPowerMessage(final Robot robot, final boolean power) {
        this(robot.getId(), power);
    }

    public RobotPowerMessage(final FriendlyByteBuf buffer) {
        this(buffer.readVarInt(), buffer.readBoolean());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeVarInt(entityId);
        buffer.writeBoolean(power);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {

    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {
        MessageUtils.withNearbyServerEntity(context, entityId, Robot.class,
            robot -> {
                if (power) {
                    robot.start();
                } else {
                    robot.stop();
                }
            });
    }
}
