package net.satisfy.nethervinery.fabric;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class NetherVineryExpectPlatformImpl {
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
