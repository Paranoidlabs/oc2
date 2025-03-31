/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.blockentity;

import li.cil.oc2.api.API;
import li.cil.oc2.common.block.Blocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class BlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, API.MOD_ID);

    ///////////////////////////////////////////////////////////////////

    public static final Supplier<BlockEntityType<BusCableBlockEntity>> BUS_CABLE = register(Blocks.BUS_CABLE, BusCableBlockEntity::new);
    public static final Supplier<BlockEntityType<ChargerBlockEntity>> CHARGER = register(Blocks.CHARGER, ChargerBlockEntity::new);
    public static final Supplier<BlockEntityType<ComputerBlockEntity>> COMPUTER = register(Blocks.COMPUTER, ComputerBlockEntity::new);
    public static final Supplier<BlockEntityType<MonitorBlockEntity>> MONITOR = register(Blocks.MONITOR, MonitorBlockEntity::new);
    public static final Supplier<BlockEntityType<CreativeEnergyBlockEntity>> CREATIVE_ENERGY = register(Blocks.CREATIVE_ENERGY, CreativeEnergyBlockEntity::new);
    public static final Supplier<BlockEntityType<DiskDriveBlockEntity>> DISK_DRIVE = register(Blocks.DISK_DRIVE, DiskDriveBlockEntity::new);
    public static final Supplier<BlockEntityType<FlashMemoryFlasherBlockEntity>> FLASH_MEMORY_FLASHER = register(Blocks.FLASH_MEMORY_FLASHER, FlashMemoryFlasherBlockEntity::new);
    public static final Supplier<BlockEntityType<KeyboardBlockEntity>> KEYBOARD = register(Blocks.KEYBOARD, KeyboardBlockEntity::new);
    public static final Supplier<BlockEntityType<NetworkConnectorBlockEntity>> NETWORK_CONNECTOR = register(Blocks.NETWORK_CONNECTOR, NetworkConnectorBlockEntity::new);
    public static final Supplier<BlockEntityType<NetworkHubBlockEntity>> NETWORK_HUB = register(Blocks.NETWORK_HUB, NetworkHubBlockEntity::new);
    public static final Supplier<BlockEntityType<ProjectorBlockEntity>> PROJECTOR = register(Blocks.PROJECTOR, ProjectorBlockEntity::new);
    public static final Supplier<BlockEntityType<RedstoneInterfaceBlockEntity>> REDSTONE_INTERFACE = register(Blocks.REDSTONE_INTERFACE, RedstoneInterfaceBlockEntity::new);
    public static final Supplier<BlockEntityType<VxlanBlockEntity>> VXLAN_HUB = register(Blocks.VXLAN_HUB, VxlanBlockEntity::new);
    public static final Supplier<BlockEntityType<PciCardCageBlockEntity>> PCI_CARD_CAGE = register(Blocks.PCI_CARD_CAGE, PciCardCageBlockEntity::new);

    ///////////////////////////////////////////////////////////////////

    public static void initialize(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
        modEventBus.addListener(BlockEntities::registerCapabilities);
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, COMPUTER.get(), ComputerBlockEntity::getEnergyStorage);
    }

    ///////////////////////////////////////////////////////////////////

    @SuppressWarnings("ConstantConditions") // .build(null) is fine
    private static <B extends Block, T extends BlockEntity> Supplier<BlockEntityType<T>> register(final DeferredBlock<B> block, final BlockEntityType.BlockEntitySupplier<T> factory) {
        return BLOCK_ENTITIES.register(block.getId().getPath(), () -> BlockEntityType.Builder.of(factory, block.get()).build(null));
    }
}
