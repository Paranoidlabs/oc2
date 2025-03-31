package li.cil.oc2.common.capabilities;

import li.cil.oc2.api.API;
import li.cil.oc2.api.bus.DeviceBus;
import li.cil.oc2.api.capabilities.NetworkInterface;
import li.cil.oc2.api.capabilities.RedstoneEmitter;
import li.cil.oc2.api.capabilities.Robot;
import li.cil.oc2.api.capabilities.TerminalUserProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Capabilities {
    public static final class Networked {
        public static final BlockCapability<NetworkInterface, @Nullable Direction> BLOCK = BlockCapability.createSided(
                create("network_interface"),
                NetworkInterface.class);
        public static final ItemCapability<NetworkInterface, Void> ITEM = ItemCapability.createVoid(
                create("network_interface"),
                NetworkInterface.class);
    }

    public static final class Device {
        public static final BlockCapability<li.cil.oc2.api.bus.device.Device, Void> BLOCK = BlockCapability.createVoid(
                create("device"),
                li.cil.oc2.api.bus.device.Device.class);
        public static final ItemCapability<li.cil.oc2.api.bus.device.Device, Void> ITEM = ItemCapability.createVoid(
                create("device"),
                li.cil.oc2.api.bus.device.Device.class);
    }

    public static final class DeviceBus {
        public static final BlockCapability<li.cil.oc2.api.bus.DeviceBusElement, Direction> BLOCK = BlockCapability.createSided(
                create("device_bus"),
                li.cil.oc2.api.bus.DeviceBusElement.class);
    }

    public static final class TerminalUserProvider {
        public static final BlockCapability<li.cil.oc2.api.capabilities.TerminalUserProvider, Void> BLOCK = BlockCapability.createVoid(
                create("terminal_user"),
                li.cil.oc2.api.capabilities.TerminalUserProvider.class);
        public static final EntityCapability<li.cil.oc2.api.capabilities.TerminalUserProvider, Void> ENTITY = EntityCapability.createVoid(
                create("terminal_user"),
                li.cil.oc2.api.capabilities.TerminalUserProvider.class);
    }

    public static final class RedstoneEmitter {
        public static final BlockCapability<li.cil.oc2.api.capabilities.RedstoneEmitter, @Nullable Direction> BLOCK = BlockCapability.createSided(
                create("redstone_emitter"),
                li.cil.oc2.api.capabilities.RedstoneEmitter.class);
    }

    public static final class Robot {
        public static final EntityCapability<li.cil.oc2.api.capabilities.Robot, Void> ENTITY = EntityCapability.createVoid(
                create("robot"),
                li.cil.oc2.api.capabilities.Robot.class);
    }

    private static ResourceLocation create(String path) {
        return new ResourceLocation(API.MOD_ID, path);
    }

    private Capabilities() {}
}
