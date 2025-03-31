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
        registrar.play(ComputerBootErrorMessage.ID, ComputerBootErrorMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(BusInterfaceNameMessage.ID, BusInterfaceNameMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(ClientCanceledImportFileMessage.ID, ClientCanceledImportFileMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(ComputerTerminalBlockMessage.ID, ComputerTerminalBlockMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(RobotTerminalMessage.ID, RobotTerminalMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));
        registrar.play(DiskDriveFloppyMessage.ID, DiskDriveFloppyMessage::new, handler -> handler
                .client(ClientPayloadHandler.getInstance()::handle)
                .server(ServerPayloadHandler.getInstance()::handle));

        /*
        registerMessage(ComputerTerminalOutputMessage.class, ComputerTerminalOutputMessage::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(ComputerTerminalInputMessage.class, ComputerTerminalInputMessage::new, NetworkDirection.PLAY_TO_SERVER);
        registerMessage(ComputerRunStateMessage.class, ComputerRunStateMessage::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(ComputerBusStateMessage.class, ComputerBusStateMessage::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(ComputerBootErrorMessage.class, ComputerBootErrorMessage::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(ComputerPowerMessage.class, ComputerPowerMessage::new, NetworkDirection.PLAY_TO_SERVER);
        registerMessage(MonitorPowerMessage.class, MonitorPowerMessage::new, NetworkDirection.PLAY_TO_SERVER);
        registerMessage(MonitorPowerMessageForwarded.class, MonitorPowerMessageForwarded::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(OpenComputerInventoryMessage.class, OpenComputerInventoryMessage::new, NetworkDirection.PLAY_TO_SERVER);
        registerMessage(OpenComputerTerminalMessage.class, OpenComputerTerminalMessage::new, NetworkDirection.PLAY_TO_SERVER);

        registerMessage(NetworkConnectorConnectionsMessage.class, NetworkConnectorConnectionsMessage::new, NetworkDirection.PLAY_TO_CLIENT);

        registerMessage(RobotTerminalOutputMessage.class, RobotTerminalOutputMessage::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(RobotTerminalInputMessage.class, RobotTerminalInputMessage::new, NetworkDirection.PLAY_TO_SERVER);
        registerMessage(RobotRunStateMessage.class, RobotRunStateMessage::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(RobotBusStateMessage.class, RobotBusStateMessage::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(RobotBootErrorMessage.class, RobotBootErrorMessage::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(RobotPowerMessage.class, RobotPowerMessage::new, NetworkDirection.PLAY_TO_SERVER);
        registerMessage(RobotInitializationRequestMessage.class, RobotInitializationRequestMessage::new, NetworkDirection.PLAY_TO_SERVER);
        registerMessage(RobotInitializationMessage.class, RobotInitializationMessage::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(OpenRobotInventoryMessage.class, OpenRobotInventoryMessage::new, NetworkDirection.PLAY_TO_SERVER);
        registerMessage(OpenRobotTerminalMessage.class, OpenRobotTerminalMessage::new, NetworkDirection.PLAY_TO_SERVER);

        registerMessage(DiskDriveFloppyMessage.class, DiskDriveFloppyMessage::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(FirmwareFlasherMessage.class, FirmwareFlasherMessage::new, NetworkDirection.PLAY_TO_CLIENT);


        registerMessage(ExportedFileMessage.class, ExportedFileMessage::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(RequestImportedFileMessage.class, RequestImportedFileMessage::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(ImportedFileMessage.class, ImportedFileMessage::new, NetworkDirection.PLAY_TO_SERVER);
        registerMessage(ServerCanceledImportFileMessage.class, ServerCanceledImportFileMessage::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(ClientCanceledImportFileMessage.class, ClientCanceledImportFileMessage::new, NetworkDirection.PLAY_TO_SERVER);

        registerMessage(BusCableFacadeMessage.class, BusCableFacadeMessage::new, NetworkDirection.PLAY_TO_CLIENT);

        registerMessage(NetworkInterfaceCardConfigurationMessage.class, NetworkInterfaceCardConfigurationMessage::new, NetworkDirection.PLAY_TO_SERVER);
        registerMessage(NetworkTunnelLinkMessage.class, NetworkTunnelLinkMessage::new, NetworkDirection.PLAY_TO_SERVER);

        registerMessage(MonitorRequestFramebufferMessage.class, MonitorRequestFramebufferMessage::new, NetworkDirection.PLAY_TO_SERVER);
        registerMessage(MonitorFramebufferMessage.class, MonitorFramebufferMessage::new, NetworkDirection.PLAY_TO_CLIENT);

        registerMessage(ProjectorRequestFramebufferMessage.class, ProjectorRequestFramebufferMessage::new, NetworkDirection.PLAY_TO_SERVER);
        registerMessage(ProjectorFramebufferMessage.class, ProjectorFramebufferMessage::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(ProjectorStateMessage.class, ProjectorStateMessage::new, NetworkDirection.PLAY_TO_CLIENT);
        registerMessage(MonitorStateMessage.class, MonitorStateMessage::new, NetworkDirection.PLAY_TO_CLIENT);

        registerMessage(KeyboardInputMessage.class, KeyboardInputMessage::new, NetworkDirection.PLAY_TO_SERVER);

        registerMessage(MonitorInputMessage.class, MonitorInputMessage::new, NetworkDirection.PLAY_TO_SERVER);

        registerMessage(MultipartMessage.class, MultipartMessage::new, NetworkDirection.PLAY_TO_SERVER);

        MultipartMessage.registerMessage(ImportedFileMessage.class, ImportedFileMessage::new);

         */
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
