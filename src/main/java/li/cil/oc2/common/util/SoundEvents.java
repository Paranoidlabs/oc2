/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.util;

import li.cil.oc2.api.API;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class SoundEvents {
    private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, API.MOD_ID);

    ///////////////////////////////////////////////////////////////////

    public static final Supplier<SoundEvent> COMPUTER_RUNNING = register("computer_running");
    public static final Supplier<SoundEvent> FLOPPY_ACCESS = register("floppy_access");
    public static final Supplier<SoundEvent> FLOPPY_EJECT = register("floppy_eject");
    public static final Supplier<SoundEvent> FLOPPY_INSERT = register("floppy_insert");
    public static final Supplier<SoundEvent> HDD_ACCESS = register("hdd_access");

    ///////////////////////////////////////////////////////////////////

    public static void initialize(IEventBus modEventBus) {
        SOUNDS.register(modEventBus);
    }

    ///////////////////////////////////////////////////////////////////

    private static Supplier<SoundEvent> register(final String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(API.MOD_ID, name)));
    }
}
