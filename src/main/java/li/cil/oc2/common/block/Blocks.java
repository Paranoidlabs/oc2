/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.block;

import com.mojang.serialization.MapCodec;
import li.cil.oc2.api.API;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class Blocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(API.MOD_ID);
    public static final DeferredRegister<MapCodec<? extends Block>> CODECS = DeferredRegister.create(BuiltInRegistries.BLOCK_TYPE, API.MOD_ID);


    ///////////////////////////////////////////////////////////////////

    private static final BlockBehaviour.Properties DEFAULT_PROPERTIES = BlockBehaviour.Properties
            .of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .strength(1.5f, 6.0f);

    public static final DeferredBlock<CreativeEnergyBlock> CREATIVE_ENERGY = BLOCKS.registerBlock("creative_energy", CreativeEnergyBlock::new, DEFAULT_PROPERTIES
            .strength(-1, 3600000)
            .noLootTable());

    public static final DeferredBlock<BusCableBlock> BUS_CABLE = BLOCKS.registerBlock("bus_cable", BusCableBlock::new, DEFAULT_PROPERTIES);
    public static final DeferredBlock<ChargerBlock> CHARGER = BLOCKS.registerBlock("charger", ChargerBlock::new, DEFAULT_PROPERTIES);
    public static final DeferredBlock<ComputerBlock> COMPUTER = BLOCKS.registerBlock("computer", ComputerBlock::new, DEFAULT_PROPERTIES);
    public static final DeferredBlock<DiskDriveBlock> DISK_DRIVE = BLOCKS.registerBlock("disk_drive", DiskDriveBlock::new, DEFAULT_PROPERTIES);
    public static final DeferredBlock<FlashMemoryFlasherBlock> FLASH_MEMORY_FLASHER = BLOCKS.registerBlock("flash_memory_flasher", FlashMemoryFlasherBlock::new, DEFAULT_PROPERTIES);
    public static final DeferredBlock<KeyboardBlock> KEYBOARD = BLOCKS.registerBlock("keyboard", KeyboardBlock::new, DEFAULT_PROPERTIES);
    public static final DeferredBlock<MonitorBlock> MONITOR = BLOCKS.registerBlock("monitor", MonitorBlock::new, DEFAULT_PROPERTIES);
    public static final DeferredBlock<NetworkConnectorBlock> NETWORK_CONNECTOR = BLOCKS.registerBlock("network_connector", NetworkConnectorBlock::new, DEFAULT_PROPERTIES);
    public static final DeferredBlock<NetworkHubBlock> NETWORK_HUB = BLOCKS.registerBlock("network_hub", NetworkHubBlock::new, DEFAULT_PROPERTIES);
    public static final DeferredBlock<ProjectorBlock> PROJECTOR = BLOCKS.registerBlock("projector", ProjectorBlock::new, DEFAULT_PROPERTIES);
    public static final DeferredBlock<RedstoneInterfaceBlock> REDSTONE_INTERFACE = BLOCKS.registerBlock("redstone_interface", RedstoneInterfaceBlock::new, DEFAULT_PROPERTIES);
    public static final DeferredBlock<VxlanBlock> VXLAN_HUB = BLOCKS.registerBlock("vxlan_hub", VxlanBlock::new, DEFAULT_PROPERTIES);
    public static final DeferredBlock<PciCardCageBlock> PCI_CARD_CAGE = BLOCKS.registerBlock("pci_card_cage", PciCardCageBlock::new, DEFAULT_PROPERTIES);

    ///////////////////////////////////////////////////////////////////

    public static final Supplier<MapCodec<BusCableBlock>> BUS_CABLE_CODEC = CODECS.register("bus_cable",
            () -> BlockBehaviour.simpleCodec(BusCableBlock::new));
    public static final Supplier<MapCodec<ChargerBlock>> CHARGER_CODEC = CODECS.register("charger",
            () -> BlockBehaviour.simpleCodec(ChargerBlock::new));
    public static final Supplier<MapCodec<ComputerBlock>> COMPUTER_CODEC = CODECS.register("computer",
            () -> BlockBehaviour.simpleCodec(ComputerBlock::new));
    public static final Supplier<MapCodec<CreativeEnergyBlock>> CREATIVE_ENERGY_CODEC = CODECS.register("creative_energy",
            () -> BlockBehaviour.simpleCodec(CreativeEnergyBlock::new));
    public static final Supplier<MapCodec<DiskDriveBlock>> DISK_DRIVE_CODEC = CODECS.register("disk_drive",
            () -> BlockBehaviour.simpleCodec(DiskDriveBlock::new));
    public static final Supplier<MapCodec<FlashMemoryFlasherBlock>> FLASH_MEMORY_FLASHER_CODEC = CODECS.register("flash_memory_flasher",
            () -> BlockBehaviour.simpleCodec(FlashMemoryFlasherBlock::new));
    public static final Supplier<MapCodec<KeyboardBlock>> KEYBOARD_CODEC = CODECS.register("keyboard",
            () -> BlockBehaviour.simpleCodec(KeyboardBlock::new));
    public static final Supplier<MapCodec<MonitorBlock>> MONITOR_CODEC = CODECS.register("monitor",
            () -> BlockBehaviour.simpleCodec(MonitorBlock::new));
    public static final Supplier<MapCodec<NetworkConnectorBlock>> NETWORK_CONNECTOR_CODEC = CODECS.register("network_connector",
            () -> BlockBehaviour.simpleCodec(NetworkConnectorBlock::new));
    public static final Supplier<MapCodec<NetworkHubBlock>> NETWORK_HUB_CODEC = CODECS.register("network_hub",
            () -> BlockBehaviour.simpleCodec(NetworkHubBlock::new));
    public static final Supplier<MapCodec<ProjectorBlock>> PROJECTOR_CODEC = CODECS.register("projector",
            () -> BlockBehaviour.simpleCodec(ProjectorBlock::new));
    public static final Supplier<MapCodec<RedstoneInterfaceBlock>> REDSTONE_INTERFACE_CODEC = CODECS.register("redstone_interface",
            () -> BlockBehaviour.simpleCodec(RedstoneInterfaceBlock::new));
    public static final Supplier<MapCodec<VxlanBlock>> VXLAN_CODEC = CODECS.register("vxlan_hub",
            () -> BlockBehaviour.simpleCodec(VxlanBlock::new));
    public static final Supplier<MapCodec<PciCardCageBlock>> PCI_CARD_CAGE_CODEC = CODECS.register("pci_card_cage",
            () -> BlockBehaviour.simpleCodec(PciCardCageBlock::new));

    ///////////////////////////////////////////////////////////////////

    public static void initialize(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        CODECS.register(modEventBus);
    }
}
