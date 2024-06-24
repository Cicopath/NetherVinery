package net.satisfy.nethervinery.registry;

import net.satisfy.vinery.block.grape.GrapeType;
import net.satisfy.vinery.registry.GrapeTypeRegistry;


public class NetherGrapeTypes {

    public static final GrapeType CRIMSON = GrapeTypeRegistry.registerGrapeType("crimson");
    public static final GrapeType WARPED = GrapeTypeRegistry.registerGrapeType("warped");

    public static void register() {
    }

    public static void addGrapeAttributes() {
        CRIMSON.setItems(NetherObjectRegistry.CRIMSON_GRAPE, NetherObjectRegistry.CRIMSON_GRAPE_SEEDS, NetherObjectRegistry.CRIMSON_GRAPEJUICE);
        WARPED.setItems(NetherObjectRegistry.WARPED_GRAPE, NetherObjectRegistry.WARPED_GRAPE_SEEDS, NetherObjectRegistry.WARPED_GRAPEJUICE);
    }
}
