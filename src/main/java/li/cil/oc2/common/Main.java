/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common;

import li.cil.ceres.Ceres;
import li.cil.oc2.api.API;
import li.cil.oc2.client.ClientSetup;
import li.cil.oc2.client.manual.Manuals;
import li.cil.oc2.common.block.Blocks;
import li.cil.oc2.common.blockentity.BlockEntities;
import li.cil.oc2.common.bus.device.DeviceTypes;
import li.cil.oc2.common.bus.device.data.BlockDeviceDataRegistry;
import li.cil.oc2.common.bus.device.data.FirmwareRegistry;
import li.cil.oc2.common.bus.device.provider.ProviderRegistry;
import li.cil.oc2.common.container.Containers;
import li.cil.oc2.common.entity.Entities;
import li.cil.oc2.common.item.ItemGroup;
import li.cil.oc2.common.item.Items;
import li.cil.oc2.common.item.crafting.RecipeSerializers;
import li.cil.oc2.common.serialization.ceres.Serializers;
import li.cil.oc2.common.tags.BlockTags;
import li.cil.oc2.common.tags.ItemTags;
import li.cil.oc2.common.util.RegistryUtils;
import li.cil.oc2.common.util.SoundEvents;
import li.cil.oc2.common.vm.provider.DeviceTreeProviders;
import li.cil.sedna.Sedna;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(API.MOD_ID)
public final class Main {
    public Main(IEventBus modEventBus) {
        Ceres.initialize();
        Sedna.initialize();
        DeviceTreeProviders.initialize();
        Serializers.initialize();

        ConfigManager.add(Config::new);
        ConfigManager.initialize();

        RegistryUtils.begin();

        ItemTags.initialize();
        BlockTags.initialize();
        Items.initialize(modEventBus);
        Blocks.initialize(modEventBus);
        BlockEntities.initialize(modEventBus);
        Entities.initialize(modEventBus);
        Containers.initialize(modEventBus);
        RecipeSerializers.initialize(modEventBus);
        SoundEvents.initialize(modEventBus);

        ProviderRegistry.initialize(modEventBus);
        DeviceTypes.initialize(modEventBus);

        BlockDeviceDataRegistry.initialize(modEventBus);
        FirmwareRegistry.initialize(modEventBus);

        RegistryUtils.finish(modEventBus);

        CommonSetup.initialize(modEventBus);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            Manuals.initialize(modEventBus);
            modEventBus.register(ClientSetup.class);
        }
        ItemGroup.initialize(modEventBus);
    }
}
