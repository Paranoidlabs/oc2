package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.ComputerBlockEntity;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.nio.ByteBuffer;

public record ComputerTerminalBlockMessage(BlockPos pos, byte[] data) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "computer_terminal_block");

    public ComputerTerminalBlockMessage(final BlockEntity entity, byte[] data) {
        this(entity.getBlockPos(), data);
    }
    public ComputerTerminalBlockMessage(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readByteArray());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeByteArray(data);
    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {
        MessageUtils.withClientBlockEntityAt(pos, ComputerBlockEntity.class,
                computer -> computer.getTerminal().putOutput(ByteBuffer.wrap(data)));
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {
        MessageUtils.withNearbyServerBlockEntityForInteraction(context, pos, ComputerBlockEntity.class,
                (player, computer) -> computer.getTerminal().putInput(ByteBuffer.wrap(data)));
    }
}
