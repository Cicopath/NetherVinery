package net.satisfy.nethervinery.registry;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.Util;
import de.cristelknight.doapi.common.block.FacingBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.satisfy.nethervinery.NetherVinery;
import net.satisfy.nethervinery.block.*;
import net.satisfy.nethervinery.util.NetherVineryIdentifier;
import net.satisfy.vinery.block.GrapevinePotBlock;
import net.satisfy.vinery.block.stem.LatticeBlock;
import net.satisfy.vinery.block.stem.PaleStemBlock;
import net.satisfy.vinery.block.storage.BigBottleStorageBlock;
import net.satisfy.vinery.block.storage.FourBottleStorageBlock;
import net.satisfy.vinery.block.storage.NineBottleStorageBlock;
import net.satisfy.vinery.block.storage.WineBottleBlock;
import net.satisfy.vinery.item.DrinkBlockBigItem;
import net.satisfy.vinery.item.DrinkBlockSmallItem;
import net.satisfy.vinery.item.GrapeBushSeedItem;
import net.satisfy.vinery.item.GrapeItem;
import net.satisfy.vinery.registry.MobEffectRegistry;
import net.satisfy.vinery.util.FoodComponent;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class NetherObjectRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(NetherVinery.MODID, Registries.ITEM);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(NetherVinery.MODID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();


    public static final RegistrySupplier<Block> OBSIDIAN_STEM = registerWithItem("obsidian_stem", () -> new PaleStemBlock(getGrapevineSettings()));
    public static final RegistrySupplier<Item> CRIMSON_NETHER_BAG = registerI("crimson_nether_bag", () -> new BundleItem(getSettings().stacksTo(1)));
    public static final RegistrySupplier<Item> WARPED_NETHER_BAG = registerI("warped_nether_bag", () -> new BundleItem(getSettings().stacksTo(1)));
    public static final RegistrySupplier<Block> CRIMSON_GRAPE_BUSH = registerB("crimson_grape_bush", () -> new CrimsonGrapeBush(getBushSettings(), NetherGrapeTypes.CRIMSON));
    public static final RegistrySupplier<Block> WARPED_GRAPE_BUSH = registerB("warped_grape_bush", () -> new WarpedGrapeBush(getBushSettings(), NetherGrapeTypes.WARPED));
    public static final RegistrySupplier<Item> CRIMSON_GRAPE_SEEDS = registerI("crimson_grape_seeds", () -> new GrapeBushSeedItem(CRIMSON_GRAPE_BUSH.get(), getSettings(), NetherGrapeTypes.CRIMSON));
    public static final RegistrySupplier<Item> CRIMSON_GRAPE = registerI("crimson_grape", () -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), NetherGrapeTypes.CRIMSON, CRIMSON_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Item> WARPED_GRAPE_SEEDS = registerI("warped_grape_seeds", () -> new GrapeBushSeedItem(WARPED_GRAPE_BUSH.get(), getSettings(), NetherGrapeTypes.WARPED));
    public static final RegistrySupplier<Item> WARPED_GRAPE = registerI("warped_grape", () -> new GrapeItem(getSettings().food(Foods.SWEET_BERRIES), NetherGrapeTypes.WARPED, WARPED_GRAPE_SEEDS.get()));
    public static final RegistrySupplier<Block> WARPED_GRAPE_CRATE = registerWithItem("warped_grape_crate", () -> new FacingBlock(BlockBehaviour.Properties.copy(Blocks.RED_WOOL)));
    public static final RegistrySupplier<Block> CRIMSON_GRAPE_GRATE = registerWithItem("crimson_grape_crate", () -> new FacingBlock(BlockBehaviour.Properties.copy(Blocks.RED_WOOL)));
    public static final RegistrySupplier<Item> WARPED_GRAPEJUICE = registerI("warped_grapejuice", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> CRIMSON_GRAPEJUICE = registerI("crimson_grapejuice", () -> new Item(getSettings()));
    public static final RegistrySupplier<Block> GHASTLY_GRENACHE = registerB("ghastly_grenache", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Item>  GHASTLY_GRENACHE_ITEM = registerI("ghastly_grenache", () -> new DrinkBlockBigItem(GHASTLY_GRENACHE.get(), getWineItemSettings(MobEffectRegistry.IMPROVED_JUMP_BOOST.get(), 1200)));
    public static final RegistrySupplier<Block> NETHERITE_NECTAR = registerB("netherite_nectar", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  NETHERITE_NECTAR_ITEM = registerI("netherite_nectar", () -> new DrinkBlockSmallItem(NETHERITE_NECTAR.get(), getWineItemSettings(NetherEffects.NETHERITE.get(), 240)));
    public static final RegistrySupplier<Block> BLAZEWINE_PINOT = registerB("blazewine_pinot", () -> new WineBottleBlock(getWineSettings(), 1));
    public static final RegistrySupplier<Item>  BLAZEWINE_PINOT_ITEM = registerI("blazewine_pinot", () -> new DrinkBlockBigItem(BLAZEWINE_PINOT.get(), getWineItemSettings(MobEffectRegistry.LAVA_WALKER.get(), 1200)));
    public static final RegistrySupplier<Block> NETHER_FIZZ = registerB("nether_fizz", () -> new WineBottleBlock(getWineSettings(), 2));
    public static final RegistrySupplier<Item>  NETHER_FIZZ_ITEM = registerI("nether_fizz", () -> new DrinkBlockSmallItem(NETHER_FIZZ.get(), getWineItemSettings(NetherEffects.HEARTHSTONE.get(), 1)));
    public static final RegistrySupplier<Block> LAVA_FIZZ = registerB("lava_fizz", () -> new WineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  LAVA_FIZZ_ITEM = registerI("lava_fizz", () -> new DrinkBlockSmallItem(LAVA_FIZZ.get(), getWineItemSettings(NetherEffects.GRAVEDIGGER.get(), 1)));
    public static final RegistrySupplier<Block> IMPROVED_NETHER_FIZZ = registerB("improved_nether_fizz", () -> new ImprovedWineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  IMPROVED_NETHER_FIZZ_ITEM = registerI("improved_nether_fizz", () -> new DrinkBlockSmallItem(IMPROVED_NETHER_FIZZ.get(), getWineItemSettings(NetherEffects.IMPROVED_HEARTHSTONE.get(), 1)));
    public static final RegistrySupplier<Block> IMPROVED_LAVA_FIZZ = registerB("improved_lava_fizz", () -> new ImprovedWineBottleBlock(getWineSettings(), 3));
    public static final RegistrySupplier<Item>  IMPROVED_LAVA_FIZZ_ITEM = registerI("improved_lava_fizz", () -> new DrinkBlockSmallItem(IMPROVED_LAVA_FIZZ.get(), getWineItemSettings(NetherEffects.IMPROVED_GRAVEDIGGER.get(), 1)));
    public static final RegistrySupplier<Block> CRIMSON_FERMENTATION_BARREL = registerWithItem("crimson_fermentation_barrel", () -> new AgingBarrelBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final RegistrySupplier<Block> CRIMSON_GRAPEVINE_POT = registerWithItem("crimson_grapevine_pot", () -> new GrapevinePotBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS).noOcclusion()));
    public static final RegistrySupplier<Block> CRIMSON_APPLE_PRESS = registerWithItem("crimson_apple_press", () -> new ApplePressBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS).noOcclusion()));
    public static final RegistrySupplier<Block> CRIMSON_WINE_RACK_BIG = registerWithItem("crimson_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> CRIMSON_WINE_RACK_SMALL = registerWithItem("crimson_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> CRIMSON_WINE_RACK_MID = registerWithItem("crimson_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> REINFORCED_CRIMSON_PLANKS = registerWithItem("reinforced_crimson_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS)));
    public static final RegistrySupplier<Block> CRESTED_CRIMSON_PLANKS = registerWithItem("crested_crimson_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS)));
    public static final RegistrySupplier<Block> CRIMSON_BARREL = registerWithItem("crimson_barrel", () -> new BarrelBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)));
    public static final RegistrySupplier<Block> WARPED_FERMENTATION_BARREL = registerWithItem("warped_fermentation_barrel", () -> new AgingBarrelBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).noOcclusion()));
    public static final RegistrySupplier<Block> WARPED_GRAPEVINE_POT = registerWithItem("warped_grapevine_pot", () -> new GrapevinePotBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS).noOcclusion()));
    public static final RegistrySupplier<Block> WARPED_APPLE_PRESS = registerWithItem("warped_apple_press", () -> new ApplePressBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS).noOcclusion()));
    public static final RegistrySupplier<Block> WARPED_WINE_RACK_BIG = registerWithItem("warped_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> WARPED_WINE_RACK_SMALL = registerWithItem("warped_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> WARPED_WINE_RACK_MID = registerWithItem("warped_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> REINFORCED_WARPED_PLANKS = registerWithItem("reinforced_warped_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS)));
    public static final RegistrySupplier<Block> CRESTED_WARPED_PLANKS = registerWithItem("crested_warped_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS)));
    public static final RegistrySupplier<Block> WARPED_BARREL = registerWithItem("warped_barrel", () -> new BarrelBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)));
    public static final RegistrySupplier<Block> WARPED_LATTICE = registerWithItem("warped_lattice", () -> new LatticeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(Blocks.JUNGLE_PLANKS.getSoundType(Blocks.JUNGLE_PLANKS.defaultBlockState())).noOcclusion()));
    public static final RegistrySupplier<Block> CRIMSON_LATTICE = registerWithItem("crimson_lattice", () -> new LatticeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(Blocks.MANGROVE_PLANKS.getSoundType(Blocks.MANGROVE_PLANKS.defaultBlockState())).noOcclusion()));


    private static <T extends Item> RegistrySupplier<T> registerI(String path, Supplier<T> item) {
        final ResourceLocation id = new NetherVineryIdentifier(path);
        return ITEM_REGISTRAR.register(id, item);
    }

    private static <T extends Block> RegistrySupplier<T> registerB(String path, Supplier<T> block) {
        final ResourceLocation id = new NetherVineryIdentifier(path);
        return BLOCK_REGISTRAR.register(id, block);
    }

    public static void init() {
        ITEMS.register();
        BLOCKS.register();
    }

    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }

    private static Item.Properties getSettings() {
        return getSettings(settings -> {
        });
    }

    private static BlockBehaviour.Properties getGrapevineSettings() {
        return BlockBehaviour.Properties.of().strength(3.0F).randomTicks().sound(SoundType.STONE).noOcclusion();
    }

    private static Item.Properties getWineItemSettings(MobEffect effect, int duration) {
        return getSettings().food(wineFoodComponent(effect, duration));
    }

    private static FoodProperties wineFoodComponent(MobEffect effect, int duration) {
        List<Pair<MobEffectInstance, Float>> effects = Lists.newArrayList();
        if (effect != null) effects.add(Pair.of(new MobEffectInstance(effect, duration), 1.0f));
        return new FoodComponent(effects);
    }

    private static BlockBehaviour.Properties getBushSettings() {
        return BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH);
    }

    private static BlockBehaviour.Properties getWineSettings() {
        return BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion().instabreak();
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String path, Supplier<T> block) {
        return Util.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, new NetherVineryIdentifier(path), block);
    }
}

