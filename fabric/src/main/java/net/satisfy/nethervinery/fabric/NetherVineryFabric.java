package net.satisfy.nethervinery.fabric;

import net.fabricmc.api.ModInitializer;
import net.satisfy.nethervinery.NetherVinery;
import net.satisfy.nethervinery.fabric.world.NetherVineryBiomeModification;

public class NetherVineryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NetherVinery.init();
        NetherVinery.commonSetup();
        NetherVineryBiomeModification.init();
    }
}
