/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network.message;

import li.cil.oc2.api.API;
import li.cil.oc2.common.blockentity.FlashMemoryFlasherBlockEntity;
import li.cil.oc2.common.network.MessageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record FirmwareFlasherMessage(BlockPos pos, CompoundTag data) implements CustomMessage {

    public static final ResourceLocation ID = new ResourceLocation(API.MOD_ID, "firmware_flasher");

    public FirmwareFlasherMessage(final FlashMemoryFlasherBlockEntity diskDrive) {
        this(diskDrive.getBlockPos(), diskDrive.getFloppy().getTag());
    }

    public FirmwareFlasherMessage(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readNbt());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeNbt(data);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void handleClientSide(PlayPayloadContext context) {
        MessageUtils.withClientBlockEntityAt(pos, FlashMemoryFlasherBlockEntity.class,
                diskDrive -> diskDrive.setFlashMemory(ItemStack.of(data)));
    }

    @Override
    public void handleServerSide(PlayPayloadContext context) {

    }
}
