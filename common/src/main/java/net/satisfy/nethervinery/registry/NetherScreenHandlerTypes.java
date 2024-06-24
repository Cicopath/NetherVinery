package net.satisfy.nethervinery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.satisfy.nethervinery.NetherVinery;
import net.satisfy.nethervinery.client.gui.handler.AgingBarrelGuiHandler;
import net.satisfy.nethervinery.client.gui.handler.ApplePressGuiHandler;
import net.satisfy.nethervinery.util.NetherVineryIdentifier;

import java.util.function.Supplier;

public class NetherScreenHandlerTypes {

    private static final Registrar<MenuType<?>> MENU_TYPES = DeferredRegister.create(NetherVinery.MODID, Registries.MENU).getRegistrar();

    public static final RegistrySupplier<MenuType<ApplePressGuiHandler>> APPLE_PRESS_GUI_HANDLER = register("apple_press_gui_handler", () -> new MenuType<>(ApplePressGuiHandler::new, FeatureFlags.VANILLA_SET));
    public static final RegistrySupplier<MenuType<AgingBarrelGuiHandler>> FERMENTATION_BARREL_GUI_HANDLER = register("fermentation_barrel_gui_handler", () -> new MenuType<>(AgingBarrelGuiHandler::new, FeatureFlags.VANILLA_SET));


    public static <T extends AbstractContainerMenu> RegistrySupplier<MenuType<T>> register(String name, Supplier<MenuType<T>> menuType){
        return MENU_TYPES.register(new NetherVineryIdentifier(name), menuType);
    }

    public static void init() {
    }
}
