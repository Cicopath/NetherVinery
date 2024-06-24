package net.satisfy.nethervinery;

import net.satisfy.nethervinery.event.CommonEvents;
import net.satisfy.nethervinery.registry.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetherVinery {
    public static final String MODID = "nethervinery";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static void init() {
        NetherObjectRegistry.init();
        NetherBlockEntityTypes.init();
        NetherTabRegistry.init();
        NetherGrapeTypes.addGrapeAttributes();
        NetherEffects.init();
        NetherScreenHandlerTypes.init();
        CommonEvents.init();
    }

    public static void commonSetup(){
    }
}
