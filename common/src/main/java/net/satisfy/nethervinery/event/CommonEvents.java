package net.satisfy.nethervinery.event;

import dev.architectury.event.events.common.LootEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.satisfy.nethervinery.util.LoottableInjector;
import org.jetbrains.annotations.Nullable;

public class CommonEvents {

    public static void init() {
        LootEvent.MODIFY_LOOT_TABLE.register(CommonEvents::onModifyLootTable);
    }

    private static void onModifyLootTable(@Nullable LootDataManager lootDataManager, ResourceLocation id, LootEvent.LootTableModificationContext ctx, boolean b) {
        LoottableInjector.InjectLoot(id, ctx);
    }
}
