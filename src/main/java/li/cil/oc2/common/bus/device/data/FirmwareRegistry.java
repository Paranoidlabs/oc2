/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.bus.device.data;

import li.cil.oc2.api.API;
import li.cil.oc2.api.bus.device.data.Firmware;
import li.cil.oc2.api.util.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import javax.annotation.Nullable;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class FirmwareRegistry {

    ///////////////////////////////////////////////////////////////////

    private static final Registry<Firmware> REGISTRY = new RegistryBuilder<>(Registries.FIRMWARE)
            .create();
    private static final DeferredRegister<Firmware> INITIALIZER = DeferredRegister.create(Registries.FIRMWARE, API.MOD_ID);

    ///////////////////////////////////////////////////////////////////

    public static final DeferredHolder<Firmware, BuildrootFirmware> BUILDROOT = INITIALIZER.register("buildroot", BuildrootFirmware::new);

    ///////////////////////////////////////////////////////////////////

    public static void initialize(IEventBus modEventBus) {
        modEventBus.addListener(NewRegistryEvent.class, event -> event.register(REGISTRY));
    }

    @Nullable
    public static ResourceLocation getKey(final Firmware firmware) {
        return INITIALIZER.getRegistryName();
    }

    @Nullable
    public static Firmware getValue(final ResourceLocation location) {
        return REGISTRY.get(location);
    }

    public static Stream<Firmware> values() {
        return REGISTRY.stream();
    }
}
