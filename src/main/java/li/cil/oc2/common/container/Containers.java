/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.container;

import li.cil.oc2.api.API;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class Containers {
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(BuiltInRegistries.MENU, API.MOD_ID);

    ///////////////////////////////////////////////////////////////////

    public static final Supplier<MenuType<ComputerInventoryContainer>> COMPUTER = CONTAINERS.register("computer", () -> IMenuTypeExtension.create(ComputerInventoryContainer::createClient));
    public static final Supplier<MenuType<ComputerTerminalContainer>> COMPUTER_TERMINAL = CONTAINERS.register("computer_terminal", () -> IMenuTypeExtension.create(ComputerTerminalContainer::createClient));
    public static final Supplier<MenuType<MonitorDisplayContainer>> MONITOR = CONTAINERS.register("monitor", () -> IMenuTypeExtension.create(MonitorDisplayContainer::createClient));
    public static final Supplier<MenuType<RobotInventoryContainer>> ROBOT = CONTAINERS.register("robot", () -> IMenuTypeExtension.create(RobotInventoryContainer::createClient));
    public static final Supplier<MenuType<RobotTerminalContainer>> ROBOT_TERMINAL = CONTAINERS.register("robot_terminal", () -> IMenuTypeExtension.create(RobotTerminalContainer::createClient));
    public static final Supplier<MenuType<NetworkTunnelContainer>> NETWORK_TUNNEL = CONTAINERS.register("network_tunnel", () -> IMenuTypeExtension.create(NetworkTunnelContainer::createClient));

    ///////////////////////////////////////////////////////////////////

    public static void initialize(IEventBus modEventBus) {
        CONTAINERS.register(modEventBus);
    }
}
