/* SPDX-License-Identifier: MIT */

package li.cil.oc2.common;

import li.cil.oc2.common.bus.device.rpc.RPCMethodParameterTypeAdapters;
import li.cil.oc2.common.integration.IMC;
import li.cil.oc2.common.integration.Integrations;
import li.cil.oc2.common.network.Network;
import li.cil.oc2.common.util.ServerScheduler;
import li.cil.oc2.common.vxlan.TunnelManager;
import net.neoforged.bus.api.IEventBus;

public final class CommonSetup {
    public static void initialize(IEventBus modEventBus) {
        IMC.initialize(modEventBus);
        Network.initialize(modEventBus);
        Integrations.initialize();
        RPCMethodParameterTypeAdapters.initialize();
        ServerScheduler.initialize(modEventBus);
        TunnelManager.initialize();
    }
}
