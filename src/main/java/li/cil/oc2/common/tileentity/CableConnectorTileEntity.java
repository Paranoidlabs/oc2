package li.cil.oc2.common.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;

import static li.cil.oc2.client.renderer.HangingCableRenderer.CABLE_COLOR;

public abstract class CableConnectorTileEntity extends AbstractTileEntity {
    protected CableConnectorTileEntity(final TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    public abstract Iterable<? extends CableConnection> getConnected();

    public static final class CableConnection {
        public final BlockPos blockPos;
        public final Vector3f color;

        private static String CONNECTION_R = "r";
        private static String CONNECTION_G = "g";
        private static String CONNECTION_B = "b";
        private static String CONNECTION_POS = "pos";

        public CableConnection(BlockPos blockPos, Vector3f color) {
            this.blockPos = blockPos;
            this.color = color;
        }

        public static CompoundNBT write(BlockPos blockPos, Vector3f color) {
            final CompoundNBT tag = new CompoundNBT();
            return CableConnection.save(tag, blockPos, color);
        }

        public static CompoundNBT save(final CompoundNBT tag, BlockPos blockPos, Vector3f color) {
            tag.putFloat(CONNECTION_R, color.x());
            tag.putFloat(CONNECTION_G, color.y());
            tag.putFloat(CONNECTION_B, color.z());
            tag.put(CONNECTION_POS, NBTUtil.writeBlockPos(blockPos));
            return tag;
        }

        public static CableConnection load(final CompoundNBT tag) {
            final float r = tag.getFloat(CONNECTION_R);
            final float g = tag.getFloat(CONNECTION_G);
            final float b = tag.getFloat(CONNECTION_B);
            final Vector3f color = new Vector3f(r,g,b);

            final CompoundNBT posTag = tag.getCompound(CONNECTION_POS);
            final BlockPos position = NBTUtil.readBlockPos(posTag);

            return new CableConnection(position, color);
        }
    }
}
