/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.bus.device.provider.item;

import li.cil.oc2.api.bus.device.ItemDevice;
import li.cil.oc2.api.bus.device.provider.ItemDeviceQuery;
import li.cil.oc2.common.bus.device.provider.util.AbstractItemDeviceProvider;
import net.neoforged.neoforge.capabilities.ItemCapability;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class AbstractItemStackCapabilityDeviceProvider<TCapability> extends AbstractItemDeviceProvider {
    private final ItemCapability<TCapability, Void> capabilitySupplier;

    ///////////////////////////////////////////////////////////////////

    protected AbstractItemStackCapabilityDeviceProvider(final ItemCapability<TCapability, Void> capabilitySupplier) {
        this.capabilitySupplier = capabilitySupplier;
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected Optional<ItemDevice> getItemDevice(final ItemDeviceQuery query) {
        final ItemCapability<TCapability, Void> capability = capabilitySupplier;
        if (capability == null) throw new IllegalStateException();
        final Optional<TCapability> optional = Optional.ofNullable(query.getItemStack().getCapability(capability));
        if (optional.isEmpty()) {
            return Optional.empty();
        }

        final TCapability value = optional.orElseThrow(AssertionError::new);

        return getItemDevice(query, value);
    }

    protected abstract Optional<ItemDevice> getItemDevice(ItemDeviceQuery query, TCapability value);
}
