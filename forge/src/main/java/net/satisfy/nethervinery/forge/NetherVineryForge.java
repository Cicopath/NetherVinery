package net.satisfy.nethervinery.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.satisfy.nethervinery.NetherVinery;
import net.satisfy.nethervinery.util.NetherVineryPre;

@Mod(NetherVinery.MODID)
public class NetherVineryForge {
    public NetherVineryForge() {
        EventBuses.registerModEventBus(NetherVinery.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        NetherVineryPre.preInit();
        NetherVinery.init();
        NetherVinery.commonSetup();
    }
}
