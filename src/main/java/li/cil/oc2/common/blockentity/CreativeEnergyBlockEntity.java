/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class CreativeEnergyBlockEntity extends ModBlockEntity implements TickableBlockEntity {
    private final Direction[] SIDES = Direction.values();
    private BlockCapabilityCache<IEnergyStorage, @Nullable Direction>[] sidedCache;

    ///////////////////////////////////////////////////////////////////

    public CreativeEnergyBlockEntity(final BlockPos pos, final BlockState state) {
        super(BlockEntities.CREATIVE_ENERGY.get(), pos, state);
        sidedCache = new BlockCapabilityCache[6];
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level instanceof ServerLevel server_level) {
            for (var side : SIDES) {
                final BlockPos neighborPos = getBlockPos().relative(side);
                sidedCache[side.get3DDataValue()] = BlockCapabilityCache.create(Capabilities.EnergyStorage.BLOCK, server_level, neighborPos, side.getOpposite());
            }
        }
    }

    @Override
    public void serverTick() {
        assert level != null;

        for (final var cache : sidedCache) {
            IEnergyStorage energyStorage = cache.getCapability();
            if (energyStorage != null) {
                energyStorage.receiveEnergy(Integer.MAX_VALUE, false);
            }
        }
    }
}
