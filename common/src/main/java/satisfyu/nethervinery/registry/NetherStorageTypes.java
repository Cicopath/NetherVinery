package satisfyu.nethervinery.registry;

import net.minecraft.world.level.block.Block;
import satisfyu.vinery.registry.StorageTypeRegistry;

import java.util.List;
import java.util.Set;

public class NetherStorageTypes extends StorageTypeRegistry {

    public static void registerBlocks(Set<Block> blocks) {
        blocks.add(NetherObjectRegistry.CRIMSON_WINE_RACK_BIG.get());
        blocks.add(NetherObjectRegistry.CRIMSON_WINE_RACK_SMALL.get());
        blocks.add(NetherObjectRegistry.CRIMSON_WINE_RACK_MID.get());
        blocks.add(NetherObjectRegistry.WARPED_WINE_RACK_BIG.get());
        blocks.add(NetherObjectRegistry.WARPED_WINE_RACK_MID.get());
        blocks.add(NetherObjectRegistry.WARPED_WINE_RACK_SMALL.get());
        blocks.addAll(List.of(NetherObjectRegistry.GHASTLY_GRENACHE.get(), NetherObjectRegistry.NETHERITE_NECTAR.get(), NetherObjectRegistry.BLAZEWINE_PINOT.get(), NetherObjectRegistry.LAVA_FIZZ.get(), NetherObjectRegistry.NETHER_FIZZ.get(), NetherObjectRegistry.IMPROVED_NETHER_FIZZ.get(), NetherObjectRegistry.IMPROVED_LAVA_FIZZ.get()));
    }
}
