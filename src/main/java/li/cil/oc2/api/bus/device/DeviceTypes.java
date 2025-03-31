/* SPDX-License-Identifier: MIT */

package li.cil.oc2.api.bus.device;

import li.cil.oc2.api.API;
import li.cil.oc2.api.util.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

/**
 * Lists built-in device types for convenience.
 */
public final class DeviceTypes {
    public static final Supplier<DeviceType> MEMORY = DeferredHolder.create(Registries.DEVICE_TYPE, new ResourceLocation(API.MOD_ID, "memory"));

    public static final Supplier<DeviceType> HARD_DRIVE = DeferredHolder.create(Registries.DEVICE_TYPE, new ResourceLocation(API.MOD_ID, "hard_drive"));

    public static final Supplier<DeviceType> FLASH_MEMORY = DeferredHolder.create(Registries.DEVICE_TYPE, new ResourceLocation(API.MOD_ID, "flash_memory"));
    public static final Supplier<DeviceType> CARD = DeferredHolder.create(Registries.DEVICE_TYPE, new ResourceLocation(API.MOD_ID, "card"));
    public static final Supplier<DeviceType> ROBOT_MODULE = DeferredHolder.create(Registries.DEVICE_TYPE, new ResourceLocation(API.MOD_ID, "robot_module"));

    public static final Supplier<DeviceType> FLOPPY = DeferredHolder.create(Registries.DEVICE_TYPE, new ResourceLocation(API.MOD_ID, "floppy"));
    public static final Supplier<DeviceType> NETWORK_TUNNEL = DeferredHolder.create(Registries.DEVICE_TYPE, new ResourceLocation(API.MOD_ID, "network_tunnel"));
    public static final Supplier<DeviceType> CPU = DeferredHolder.create(Registries.DEVICE_TYPE, new ResourceLocation(API.MOD_ID, "cpu"));
}
