package li.cil.oc2.common.tileentity;

import li.cil.oc2.api.capabilities.NetworkInterface;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import org.jetbrains.annotations.Nullable;

import java.io.*;

public final class TapTileEntity extends AbstractTileEntity implements NetworkInterface, ITickableTileEntity {

    private boolean initialized = false;
    private int id;
    private InputStream tapInput;
    private OutputStream tapOutput;
    private int retry = 0;

    protected TapTileEntity() {
        super(TileEntities.TAP_TILE_ENTITY.get());
        id = (Integer) (int) (Math.random() * Integer.MAX_VALUE);
    }

    @Nullable
    @Override
    public byte[] readEthernetFrame() {
        return null;
    }

    @Override
    public void writeEthernetFrame(final NetworkInterface source, final byte[] frame, final int timeToLive) {
        if (!initialized) return;
        try {
            tapOutput.write(frame);
            tapOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        if (level.isClientSide()) return;
        if (!initialized && (retry-- == 0)) {
            try {
                tapInput = new FileInputStream(String.format("/dev/net/oc2-%08x", id));
                tapOutput = new FileOutputStream(String.format("/dev/net/oc2-%08x", id));
                initialized = true;
                System.out.println("Connected to tap interface");
            } catch (Exception e) {
                System.out.println(String.format("/dev/net/oc2-%08x not found", id));
                e.printStackTrace();
                retry = 40;
            }
        }
    }
}
