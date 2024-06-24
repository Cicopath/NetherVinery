package net.satisfy.nethervinery.fabric;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.satisfy.nethervinery.util.NetherVineryPre;

public class NetherVineryFabricPre implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        NetherVineryPre.preInit();
    }
}
