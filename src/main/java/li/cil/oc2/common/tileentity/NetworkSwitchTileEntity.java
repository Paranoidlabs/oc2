package li.cil.oc2.common.tileentity;

import java.util.*;
import java.util.stream.Collectors;

import li.cil.oc2.api.bus.device.object.Callback;
import li.cil.oc2.api.bus.device.object.DocumentedDevice;
import li.cil.oc2.api.bus.device.object.NamedDevice;
import li.cil.oc2.api.bus.device.object.Parameter;
import li.cil.oc2.api.capabilities.NetworkInterface;
import li.cil.oc2.common.Constants;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;

import static java.util.Collections.singletonList;

public final class NetworkSwitchTileEntity extends NetworkHubTileEntity implements ITickableTileEntity, NamedDevice, DocumentedDevice {
    private final String GET_HOST_TABLE = "getHostTable";

    private final long HOST_TTL = 20 * 60 * 2;
    private final Map<Long, HostEntry> hostTable = new HashMap<>();
    private int tickCount = 0;

    public NetworkSwitchTileEntity() {
        super(TileEntities.NETWORK_SWITCH_TILE_ENTITY.get());
        hostTable.put(0L, new HostEntry(0, Long.MAX_VALUE));
    }

    @Override
    public void writeEthernetFrame(final NetworkInterface source, final byte[] frame, final int timeToLive) {
        if (areAdjacentInterfacesDirty) {
            hostTable.clear();
        }
        validateAdjacentInterfaces();
        long tickTime = getLevel().getGameTime();
        long destMac = macToLong(frame, 0);
        long srcMac = macToLong(frame, 6);
        Optional<Integer> side = sideReverseLookup(source);
        if (!side.isPresent()) {
            return;
        }
        if (hostTable.size() <= 256) {
            hostTable.put(srcMac, new HostEntry(side.get(), tickTime));
        }
        HostEntry host = hostTable.get(destMac);
        if (host != null) {
            if (host.iface == side.get()) {
                // if packet is to same port, drop
                return;
            }
            adjacentInterfaces[host.iface].writeEthernetFrame(this, frame, timeToLive - TTL_COST);
            host.timestamp = tickTime;
        } else {
            super.writeEthernetFrame(source, frame, timeToLive);
        }
    }

    private long macToLong(final byte[] mac, int offset) {
        long ret = 0;
        for (int i = 0; i < 6; i++) {
            ret |= (mac[i + offset] << i);
        }
        return ret;
    }

    @Override
    public void tick() {
        if (tickCount++ % 20 == 0) {
            long threshold = getLevel().getGameTime() - HOST_TTL;
            if (threshold < 0) {
                return;
            }
            hostTable.entrySet().removeIf(e -> e.getValue().timestamp < threshold);
        }
    }

    @Override
    public void getDeviceDocumentation(final DeviceVisitor visitor) {
        visitor.visitCallback(GET_HOST_TABLE)
                .description("Returns the MAC address table of the switch")
                .returnValueDescription("The MAC table. For each host the mac address, the age (in ticks) and the face is returned");
    }

    @Override
    public Collection<String> getDeviceTypeNames() {
        return singletonList("switch");
    }

    @Callback(name = GET_HOST_TABLE)
    public List<LuaHostEntry> getHostTable() {
        long now = getLevel().getGameTime();
        return hostTable
                .entrySet()
                .stream()
                .map(e -> new LuaHostEntry(macLongToString(e.getKey()), now - e.getValue().timestamp, e.getValue().iface))
                .collect(Collectors.toList());
    }

    private Optional<Integer> sideReverseLookup(NetworkInterface iface) {
        for (int i = 0; i < Constants.BLOCK_FACE_COUNT; i++) {
            if (iface == adjacentInterfaces[i]) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    private String macLongToString(long mac) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            if (i != 0) {
                ret.append(":");
            }
            ret.append(String.format("%02x", (mac >> i) & 0xff));
        }
        return ret.toString();
    }

    private class HostEntry {
        public int iface;
        public long timestamp;
        public HostEntry(int iface, long timestamp) {
            this.iface = iface;
            this.timestamp = timestamp;
        }
    }

    public class LuaHostEntry {
        public String mac;
        public long age;
        public int iface;

        public LuaHostEntry(String mac, long age, int iface) {
            this.mac = mac;
            this.age = age;
            this.iface = iface;
        }
    }
}
