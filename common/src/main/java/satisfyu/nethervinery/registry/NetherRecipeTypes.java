package satisfyu.nethervinery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import satisfyu.nethervinery.NetherVinery;
import satisfyu.nethervinery.NetherVineryIdentifier;
import satisfyu.nethervinery.recipe.AgingBarrelRecipe;

import java.util.function.Supplier;

public class NetherRecipeTypes {

    private static final Registrar<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(NetherVinery.MODID, Registry.RECIPE_TYPE_REGISTRY).getRegistrar();
    private static final Registrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(NetherVinery.MODID, Registry.RECIPE_SERIALIZER_REGISTRY).getRegistrar();

    public static final RegistrySupplier<RecipeType<AgingBarrelRecipe>> AGING_BARREL_RECIPE_TYPE = create("aging_barrel");
    public static final RegistrySupplier<RecipeSerializer<AgingBarrelRecipe>> AGING_BARREL_RECIPE_SERIALIZER = create("aging_barrel", AgingBarrelRecipe.Serializer::new);

    private static <T extends Recipe<?>> RegistrySupplier<RecipeSerializer<T>> create(String name, Supplier<RecipeSerializer<T>> serializer) {
        return RECIPE_SERIALIZERS.register(new NetherVineryIdentifier(name), serializer);
    }

    private static <T extends Recipe<?>> RegistrySupplier<RecipeType<T>> create(String name) {
        Supplier<RecipeType<T>> type = () -> new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        };
        return RECIPE_TYPES.register(new NetherVineryIdentifier(name), type);
    }

    public static void init() {
    }
}
