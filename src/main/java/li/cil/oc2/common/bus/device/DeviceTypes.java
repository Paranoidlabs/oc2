/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common.bus.device;

import li.cil.oc2.api.API;
import li.cil.oc2.api.bus.device.DeviceType;
import li.cil.oc2.api.util.Registries;
import li.cil.oc2.common.bus.device.util.DeviceTypeImpl;
import li.cil.oc2.common.tags.ItemTags;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import static li.cil.oc2.common.util.TranslationUtils.text;

public final class DeviceTypes {
    private static final DeferredRegister<DeviceType> INITIALIZER = DeferredRegister.create(Registries.DEVICE_TYPE, API.MOD_ID);

    ///////////////////////////////////////////////////////////////////

    private static final Registry<DeviceType> DEVICE_TYPE_REGISTRY = new RegistryBuilder<>(Registries.DEVICE_TYPE)
            .create();

    ///////////////////////////////////////////////////////////////////

    public static void initialize(IEventBus modEventBus) {
        modEventBus.addListener(NewRegistryEvent.class, event -> event.register(DEVICE_TYPE_REGISTRY));

        register(ItemTags.DEVICES_MEMORY);
        register(ItemTags.DEVICES_HARD_DRIVE);
        register(ItemTags.DEVICES_FLASH_MEMORY);
        register(ItemTags.DEVICES_CARD);
        register(ItemTags.DEVICES_ROBOT_MODULE);
        register(ItemTags.DEVICES_FLOPPY);
        register(ItemTags.DEVICES_NETWORK_TUNNEL);
        register(ItemTags.DEVICES_CPU);

        INITIALIZER.register(modEventBus);
    }

    ///////////////////////////////////////////////////////////////////

    private static void register(final TagKey<Item> tag) {
        final String id = tag.location().getPath().replaceFirst("^devices/", "");
        INITIALIZER.register(id, () -> new DeviceTypeImpl(
            tag,
            new ResourceLocation(API.MOD_ID, "item/" + id + "_slot"),
            text("gui.{mod}.device_type." + id)
        ));
    }
}
