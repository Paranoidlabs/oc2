/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.BusCableBlockEntity;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record BusInterfaceNameMessage(BlockPos pos, Direction side, String value) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "bus_interface_name");

    public BusInterfaceNameMessage(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readEnum(Direction.class), buffer.readUtf(32));
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeEnum(side);
        buffer.writeUtf(value, 32);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {
        MessageUtils.withClientBlockEntityAt(pos, BusCableBlockEntity.class,
                busCable -> busCable.setInterfaceName(side, value));
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {
        MessageUtils.withNearbyServerBlockEntityForInteraction(context, pos, BusCableBlockEntity.class,
                (player, busCable) -> busCable.setInterfaceName(side, value));
    }
}
