package li.cil.oc2.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import li.cil.oc2.client.renderer.HangingCableRenderer;
import li.cil.oc2.common.tileentity.CableConnectorTileEntity;
import li.cil.oc2.common.tileentity.NetworkConnectorTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;

public final class CableConnectorTileEntityRenderer extends TileEntityRenderer<CableConnectorTileEntity> {
    public CableConnectorTileEntityRenderer(final TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void render(final CableConnectorTileEntity connector, final float partialTicks, final MatrixStack matrixStack, final IRenderTypeBuffer buffer, final int light, final int overlay) {
        // We do cable rendering as a fall-back in the TESR when Fabulous rendering is enabled.
        // We need to do this because there's no hook to render before the Fabulous full-screen
        // effects are rendered, which, sadly, completely ruin the depth buffer for us.
        if (!Minecraft.useShaderTransparency()) {
            return;
        }

        final BlockPos from = connector.getBlockPos();

        matrixStack.pushPose();
        matrixStack.translate(-from.getX(), -from.getY(), -from.getZ());

        HangingCableRenderer.renderCablesFor(renderer.level, matrixStack, renderer.camera.getPosition(), connector);

        matrixStack.popPose();
    }
}
