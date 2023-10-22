package satisfyu.nethervinery.registry;

import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AdvancementRecipeGenerator {

    public static String FOLDER = "/Users/marco/Desktop/Neuer Ordner/";

    public static void main(String[] args) {
        List<String> putRecipesHere = List.of(

                "minecraft:leather/warped_nether_bag",
                "minecraft:warped_fungus/warped_nether_bag",
                "minecraft:leather/crimson_nether_bag",
                "minecraft:crimson_fungus/crimson_nether_bag",
                "minecraft:obsidian/obsidian_stem",
                "crimson_grape/crimson_grape_seeds",
                "warped_grape/warped_grape_seeds",
                "crimson_grape/crimson_grape_crate",
                "warped_grape/warped_grape_crate",
                "nether_fizz/improved_nether_fizz",
                "lava_fizz/improved_lava_fizz",
                "minecraft:crimson_planks/crimson_fermentation_barrel",
                "minecraft:crimson_planks/crimson_grapevine_pot",
                "minecraft:crimson_planks/crimson_apple_press",
                "minecraft:crimson_planks/crimson_wine_rack_small",
                "minecraft:crimson_planks/crimson_wine_rack_mid",
                "minecraft:crimson_planks/crimson_wine_rack_bid",
                "minecraft:crimson_planks/reinforced_crimson_planks",
                "minecraft:crimson_planks/crested_crimson_planks",
                "minecraft:warped_planks/warped_fermentation_barrel",
                "minecraft:warped_planks/warped_grapevine_pot",
                "minecraft:warped_planks/warped_apple_press",
                "minecraft:warped_planks/warped_wine_rack_small",
                "minecraft:warped_planks/warped_wine_rack_mid",
                "minecraft:warped_planks/warped_wine_rack_bid",
                "minecraft:warped_planks/reinforced_warped_planks",
                "minecraft:warped_planks/crested_warped_planks"





        );

        for(String s : putRecipesHere){
            List<String> list1 = Arrays.stream(s.split("/")).toList();

            if(list1.size() < 2){
                System.out.println("False entry: " + s);
                continue;
            }

            write(list1.get(0), list1.get(1));
        }
    }


    public static void write(String condition, String recipe) {
        String fullRecipe = "nethervinery:" + recipe;
        String fullCondition = "nethervinery:" + condition;

        if(condition.contains(":")){
            fullCondition = condition;
            condition = Arrays.stream(condition.split(":")).toList().get(1);
        }

        if(recipe.contains(":")){
            fullRecipe = condition;
            recipe = Arrays.stream(recipe.split(":")).toList().get(1);
        }



        // Writing
        try (FileWriter fileWriter = new FileWriter(FOLDER + recipe + ".json"); JsonWriter jsonWriter = new JsonWriter(fileWriter)) {
            jsonWriter.setIndent("  ");
            jsonWriter.beginObject()
                    .name("parent").value("minecraft:recipes/root")
                    .name("rewards").beginObject().name("recipes").beginArray().value(fullRecipe).endArray().endObject()
                    .name("criteria").beginObject().name("has_" + condition).beginObject().name("trigger").value("minecraft:inventory_changed").name("conditions").beginObject().name("items").beginArray().beginObject().name("items").beginArray().value(fullCondition).endArray().endObject().endArray().endObject().endObject().name("has_the_recipe").beginObject().name("trigger").value("minecraft:recipe_unlocked").name("conditions").beginObject().name("recipe").value(fullRecipe).endObject().endObject().endObject()
                    .name("requirements").beginArray().beginArray().value("has_" + condition).value("has_the_recipe").endArray().endArray()

                    .endObject();
        } catch (IOException e) {
            System.out.printf("[Nethervinery] Couldn't write recipe to " + FOLDER + recipe);
            e.printStackTrace();
        }
    }
}