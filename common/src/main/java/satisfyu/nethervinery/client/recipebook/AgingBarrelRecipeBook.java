package satisfyu.nethervinery.client.recipebook;

import de.cristelknight.doapi.client.recipebook.screen.widgets.PrivateRecipeBookWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import satisfyu.nethervinery.recipe.AgingBarrelRecipe;
import satisfyu.nethervinery.registry.NetherRecipeTypes;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.List;
@Environment(EnvType.CLIENT)
public class AgingBarrelRecipeBook extends PrivateRecipeBookWidget {
    private static final Component TOGGLE_FERMENTABLE_TEXT;

    public AgingBarrelRecipeBook() {
    }

    @Override
    public void showGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        this.ghostSlots.addSlot(recipe.getResultItem(), slots.get(5).x, slots.get(5).y);
        this.ghostSlots.addSlot(ObjectRegistry.WINE_BOTTLE.get().asItem().getDefaultInstance(), slots.get(0).x, slots.get(0).y);
        int j = 1;
        for (Ingredient ingredient : recipe.getIngredients()) {
            ItemStack[] inputStacks = ingredient.getItems();
            ItemStack inputStack = inputStacks[RandomSource.create().nextInt(0, inputStacks.length)];
            this.ghostSlots.addSlot(inputStack, slots.get(j).x, slots.get(j++).y);
        }
    }


    @Override
    protected RecipeType<? extends Recipe<Container>> getRecipeType() {
        return NetherRecipeTypes.AGING_BARREL_RECIPE_TYPE.get();
    }

    @Override
    public void insertRecipe(Recipe<?> recipe, List<Slot> slots) {
        if (recipe instanceof AgingBarrelRecipe) {
            int slotIndex = 0;
            for (Slot slot : slots) {
                if (slot.getItem().getItem() == ObjectRegistry.WINE_BOTTLE.get().asItem()) {
                    Minecraft.getInstance().gameMode.handleInventoryMouseClick(screenHandler.containerId, slotIndex, 0, ClickType.PICKUP, Minecraft.getInstance().player);
                    Minecraft.getInstance().gameMode.handleInventoryMouseClick(screenHandler.containerId, 0, 0, ClickType.PICKUP, Minecraft.getInstance().player);
                    break;
                }
                ++slotIndex;
            }
        }
        int usedInputSlots = 1;
        for (Ingredient ingredient : recipe.getIngredients()) {
            int slotIndex = 0;
            for (Slot slot : slots) {
                ItemStack itemStack = slot.getItem();

                if (ingredient.test(itemStack) && usedInputSlots < 5) {
                    Minecraft.getInstance().gameMode.handleInventoryMouseClick(screenHandler.containerId, slotIndex, 0, ClickType.PICKUP, Minecraft.getInstance().player);
                    Minecraft.getInstance().gameMode.handleInventoryMouseClick(screenHandler.containerId, usedInputSlots, 0, ClickType.PICKUP, Minecraft.getInstance().player);
                    ++usedInputSlots;
                    break;
                }
                ++slotIndex;
            }
        }
    }

    @Override
    protected Component getToggleCraftableButtonText() {
        return TOGGLE_FERMENTABLE_TEXT;
    }

    static {
        TOGGLE_FERMENTABLE_TEXT = Component.translatable("gui.vinery.recipebook.toggleRecipes.fermentable");
    }
}
