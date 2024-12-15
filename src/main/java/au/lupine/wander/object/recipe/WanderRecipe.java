package au.lupine.wander.object.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import au.lupine.wander.object.number.VariableInteger;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class WanderRecipe {

    private final int weight;
    private final VariableInteger maxUses;
    private final RecipeItem result;
    private final List<RecipeItem> ingredients = new ArrayList<>();

    public WanderRecipe(JsonObject jsonObject) {
        weight = jsonObject.get("weight").getAsInt();

        VariableInteger maxUses;
        try {
            maxUses = VariableInteger.fromJSONElement(jsonObject.get("max_uses"));
        } catch (Exception e) {
            maxUses = new VariableInteger(1, 1);
        }
        this.maxUses = maxUses;

        result = new RecipeItem(jsonObject.get("result").getAsJsonObject());

        for (JsonElement element : jsonObject.get("ingredients").getAsJsonArray()) {
            ingredients.add(new RecipeItem(element.getAsJsonObject()));
        }
    }

    public MerchantRecipe generate() {
        MerchantRecipe recipe = new MerchantRecipe(result.generate(), maxUses.generate());

        for (RecipeItem item : this.ingredients) {
            recipe.addIngredient(item.generate());
        }

        return recipe;
    }

    public int getWeight() {
        return weight;
    }
}
