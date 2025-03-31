/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.bus.device.provider.util;

import li.cil.oc2.api.bus.device.Device;
import li.cil.oc2.api.bus.device.provider.BlockDeviceQuery;
import li.cil.oc2.api.util.Invalidatable;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.BlockCapability;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class AbstractBlockEntityCapabilityDeviceProvider<TCapability, TBlockEntity extends BlockEntity> extends AbstractBlockEntityDeviceProvider<TBlockEntity> {
    private final BlockCapability<TCapability, Direction> capabilitySupplier;

    ///////////////////////////////////////////////////////////////////

    protected AbstractBlockEntityCapabilityDeviceProvider(final BlockEntityType<TBlockEntity> blockEntityType, final BlockCapability<TCapability, Direction> capabilitySupplier) {
        super(blockEntityType);
        this.capabilitySupplier = capabilitySupplier;
    }

    protected AbstractBlockEntityCapabilityDeviceProvider(final BlockCapability<TCapability, Direction> capabilitySupplier) {
        this.capabilitySupplier = capabilitySupplier;
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected final Invalidatable<Device> getBlockDevice(final BlockDeviceQuery query, final TBlockEntity blockEntity) {
        final BlockCapability<TCapability, Direction> capability = capabilitySupplier;
        if (capability == null) throw new IllegalStateException();
        final Optional<TCapability> optional = Optional.ofNullable(blockEntity.getLevel().getCapability(capability, blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity, query.getQuerySide()));
        if (!optional.isPresent()) {
            return Invalidatable.empty();
        }

        final TCapability value = optional.orElseThrow(AssertionError::new);
        final Invalidatable<Device> device = getBlockDevice(query, value);

        // When capability gets invalidated, invalidate device. But don't keep device alive via capability.
        //LazyOptionalUtils.addWeakListener(optional, device, (invalidatable, unused) -> invalidatable.invalidate());

        return device;
    }

    protected abstract Invalidatable<Device> getBlockDevice(final BlockDeviceQuery query, final TCapability value);
}
