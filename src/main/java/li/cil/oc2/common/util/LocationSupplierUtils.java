/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.util;

import li.cil.oc2.api.bus.device.provider.ItemDeviceQuery;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;
import java.util.function.Supplier;

public final class LocationSupplierUtils {
    public static Supplier<Optional<BlockLocation>> of(final BlockEntity blockEntity) {
        return () -> BlockLocation.ofOptional(blockEntity);
    }

    public static Supplier<Optional<BlockLocation>> of(final Entity entity) {
        return () -> BlockLocation.ofOptional(entity);
    }

    public static Supplier<Optional<BlockLocation>> of(final ItemDeviceQuery query) {
        final Optional<BlockEntity> blockEntity = query.getContainerBlockEntity();
        if (blockEntity.isPresent()) {
            return () -> BlockLocation.ofOptional(blockEntity.get());
        }

        final Optional<Entity> entity = query.getContainerEntity();
        if (entity.isPresent()) {
            return () -> BlockLocation.ofOptional(entity.get());
        }

        return Optional::empty;
    }
}
