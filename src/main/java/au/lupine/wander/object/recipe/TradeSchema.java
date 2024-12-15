package au.lupine.wander.object.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import au.lupine.wander.Wander;
import au.lupine.wander.object.number.VariableInteger;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TradeSchema {

    private final Integer nullWeight;
    private final VariableInteger numIterations;
    private final List<Trade> recipes = new ArrayList<>();

    private int totalWeight = 0;

    public TradeSchema(JsonObject jsonObject) {
        Integer nullWeight;
        try {
            nullWeight = jsonObject.get("null_weight").getAsInt();
            totalWeight += nullWeight;
        } catch (Exception e) {
            nullWeight = null;
        }
        this.nullWeight = nullWeight;

        VariableInteger numIterations;
        try {
            numIterations = VariableInteger.fromJSONElement(jsonObject.get("num_iterations"));
        } catch (Exception e) {
            numIterations = new VariableInteger(1, 1);
        }
        this.numIterations = numIterations;

        for (JsonElement element : jsonObject.get("recipes").getAsJsonArray()) {
            Trade recipe;
            try {
                recipe = new Trade(element.getAsJsonObject());
            } catch (Exception e) {
                Wander.logWarning("An error occurred while creating a recipe");
                for (StackTraceElement trace : e.getStackTrace()) {
                    Wander.logWarning(trace.toString());
                }

                continue;
            }

            totalWeight += recipe.getWeight();
            recipes.add(recipe);
        }
    }

    public List<MerchantRecipe> generate() {
        List<Trade> alreadyAdded = new ArrayList<>();

        List<MerchantRecipe> recipes = new ArrayList<>();
        for (int i = 0; i <= Math.min(numIterations.generate(), this.recipes.size()); i++) {
            Trade recipe = getRandomRecipe();
            if (recipe == null) continue;

            if (recipe.doesAllowDuplicates() || !alreadyAdded.contains(recipe)) {
                recipes.add(recipe.generate());
                alreadyAdded.add(recipe);
            }
        }

        return recipes;
    }

    private @Nullable Trade getRandomRecipe() {
        int randomWeight = new Random().nextInt(0, this.totalWeight + 1);

        int totalWeight = 0;

        if (nullWeight != null) {
            totalWeight += nullWeight;
            if (randomWeight <= nullWeight) return null;
        }

        for (Trade recipe : recipes) {
            int recipeWeight = recipe.getWeight();

            if (randomWeight > totalWeight && randomWeight <= totalWeight + recipeWeight)
                return recipe;

            totalWeight += recipeWeight;
        }

        try {
            return recipes.getLast(); // Hopefully this can't be reached, but just a catch for any possible weight issues
        } catch (Exception e) {
            return null;
        }
    }
}
