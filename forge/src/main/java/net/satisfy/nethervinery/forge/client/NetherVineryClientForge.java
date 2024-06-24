package net.satisfy.nethervinery.forge.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.satisfy.nethervinery.NetherVinery;
import net.satisfy.nethervinery.client.NetherVineryClient;

@Mod.EventBusSubscriber(modid = NetherVinery.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetherVineryClientForge {

    @SubscribeEvent
    public static void beforeClientSetup(RegisterEvent event) {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        NetherVineryClient.onInitializeClient();
    }

}
