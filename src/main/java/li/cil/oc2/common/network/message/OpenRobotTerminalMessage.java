/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.entity.Robot;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record OpenRobotTerminalMessage(int entityId) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "open_robot_terminal");

    public OpenRobotTerminalMessage(final Robot robot) {
        this(robot.getId());
    }

    public OpenRobotTerminalMessage(final FriendlyByteBuf buffer) {
        this(buffer.readVarInt());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeVarInt(entityId);
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
        if (context.player().isPresent()) {
            if (context.player().get() instanceof ServerPlayer server_player)
                MessageUtils.withNearbyServerEntity(context, entityId, Robot.class,
                    robot -> robot.openTerminalScreen(server_player));
        }
    }
}
