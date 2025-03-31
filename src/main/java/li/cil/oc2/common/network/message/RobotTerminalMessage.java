package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.entity.Robot;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.nio.ByteBuffer;

public record RobotTerminalMessage(int entityId, byte[] data) implements CustomMessage {
    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "robot_terminal_block");

    public RobotTerminalMessage(final FriendlyByteBuf buffer) {
        this(buffer.readVarInt(), buffer.readByteArray());
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeVarInt(entityId);
        buffer.writeByteArray(data);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {
        MessageUtils.withClientEntity(entityId, Robot.class,
                robot -> robot.getTerminal().putOutput(ByteBuffer.wrap(data)));
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {
        MessageUtils.withNearbyServerEntity(context, entityId, Robot.class,
                robot -> robot.getTerminal().putInput(ByteBuffer.wrap(data)));
    }
}
