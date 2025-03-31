/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.BusCableBlockEntity;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record BusCableFacadeMessage(BlockPos pos, ItemStack stack) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "bus_cable_facade");

    public BusCableFacadeMessage(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readItem());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeItem(stack);
    }

    @Override
    public void handleClientSide(final PlayPayloadContext context) {
        MessageUtils.withClientBlockEntityAt(pos, BusCableBlockEntity.class,
                busCable -> busCable.setFacade(stack));
    }

    @Override
    public void handleServerSide(final PlayPayloadContext context) {}
}
