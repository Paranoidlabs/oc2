/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.item.Items;
import li.cil.oc2.common.item.NetworkInterfaceCardItem;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record NetworkInterfaceCardConfigurationMessage(InteractionHand hand, Direction side, boolean value) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "network_interface_card_configuration");

    public NetworkInterfaceCardConfigurationMessage(final FriendlyByteBuf buffer) {
        this(buffer.readEnum(InteractionHand.class), buffer.readEnum(Direction.class), buffer.readBoolean());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeEnum(hand);
        buffer.writeEnum(side);
        buffer.writeBoolean(value);
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
        if (context.player().isEmpty()) {
            return;
        }
        final Player player = context.player().get();

        final ItemStack itemStack = player.getItemInHand(hand);
        if (!itemStack.is(Items.NETWORK_INTERFACE_CARD.get())) {
            return;
        }

        NetworkInterfaceCardItem.setSideConfiguration(itemStack, side, value);
    }
}
