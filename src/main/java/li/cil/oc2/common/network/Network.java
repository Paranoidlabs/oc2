/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.network;

import li.cil.oc2.api.API;
import li.cil.oc2.common.container.IntPrecisionContainerData;
import li.cil.oc2.common.network.message.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

import java.util.function.Function;

public final class Network {
    public static void initialize(IEventBus modEventBus) {
        modEventBus.addListener(Network::register);
    }
    private static void register(final RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar(API.MOD_ID);
        registrar.play(BusCableFacadeMessage.ID, BusCableFacadeMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(BusInterfaceNameMessage.ID, BusInterfaceNameMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(ClientCanceledImportFileMessage.ID, ClientCanceledImportFileMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(ComputerBootErrorMessage.ID, ComputerBootErrorMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(ComputerBusStateMessage.ID, ComputerBusStateMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(ComputerPowerMessage.ID, ComputerPowerMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(ComputerRunStateMessage.ID, ComputerRunStateMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(ComputerTerminalBlockMessage.ID, ComputerTerminalBlockMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(DiskDriveFloppyMessage.ID, DiskDriveFloppyMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(ExportedFileMessage.ID, ExportedFileMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(FirmwareFlasherMessage.ID, FirmwareFlasherMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(ImportedFileMessage.ID, ImportedFileMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(KeyboardInputMessage.ID, KeyboardInputMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(MonitorFramebufferMessage.ID, MonitorFramebufferMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(MonitorInputMessage.ID, MonitorInputMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(MonitorPowerMessage.ID, MonitorPowerMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(MonitorPowerMessageForwarded.ID, MonitorPowerMessageForwarded::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(MonitorRequestFramebufferMessage.ID, MonitorRequestFramebufferMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(MonitorStateMessage.ID, MonitorStateMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(NetworkConnectorConnectionsMessage.ID, NetworkConnectorConnectionsMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(NetworkInterfaceCardConfigurationMessage.ID, NetworkInterfaceCardConfigurationMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(NetworkTunnelLinkMessage.ID, NetworkTunnelLinkMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(OpenComputerInventoryMessage.ID, OpenComputerInventoryMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(OpenComputerTerminalMessage.ID, OpenComputerTerminalMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(OpenRobotInventoryMessage.ID, OpenRobotInventoryMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(OpenRobotTerminalMessage.ID, OpenRobotTerminalMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(ProjectorFramebufferMessage.ID, ProjectorFramebufferMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(ProjectorRequestFramebufferMessage.ID, ProjectorRequestFramebufferMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(ProjectorStateMessage.ID, ProjectorStateMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(RequestImportedFileMessage.ID, RequestImportedFileMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(RobotBootErrorMessage.ID, RobotBootErrorMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(RobotBusStateMessage.ID, RobotBusStateMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(RobotInitializationMessage.ID, RobotInitializationMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(RobotPowerMessage.ID, RobotPowerMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(RobotRunStateMessage.ID, RobotRunStateMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(RobotTerminalMessage.ID, RobotTerminalMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(ServerCanceledImportFileMessage.ID, ServerCanceledImportFileMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
    }

    // TODO remove this, it's not really all that efficient or sensible
    public static void sendToClientsTrackingBlockEntity(final CustomPacketPayload message, final BlockEntity entity) {
        PacketDistributor.TRACKING_CHUNK.with(entity.getLevel().getChunkAt(entity.getBlockPos()))
                .send(message);
    }

    public static void sendToServer(final CustomPacketPayload message) {
        PacketDistributor.SERVER.noArg().send(message);
    }

    public static void sendToClient(final CustomPacketPayload message, final ServerPlayer player) {
        PacketDistributor.PLAYER.with(player).send(message);
    }

    public static void sendToClientsTrackingEntity(final CustomPacketPayload message, final Entity entity) {
        PacketDistributor.TRACKING_ENTITY.with(entity).send(message);
    }
}
