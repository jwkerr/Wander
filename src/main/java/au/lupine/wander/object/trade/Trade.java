package au.lupine.wander.object.trade;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import au.lupine.wander.object.number.VariableInteger;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class Trade {

    private final int weight;
    private final boolean allowDuplicates;
    private final VariableInteger maxUses;
    private final TradeItem result;
    private final List<TradeItem> ingredients = new ArrayList<>();

    public Trade(JsonObject jsonObject) {
        weight = jsonObject.get("weight").getAsInt();

        if (jsonObject.has("allow_duplicates")) {
            allowDuplicates = jsonObject.get("allow_duplicates").getAsBoolean();
        } else {
            allowDuplicates = false;
        }

        VariableInteger maxUses;
        try {
            maxUses = VariableInteger.fromJSONElement(jsonObject.get("max_uses"));
        } catch (Exception e) {
            maxUses = new VariableInteger(1, 1);
        }
        this.maxUses = maxUses;

        result = new TradeItem(jsonObject.get("result").getAsJsonObject());

        for (JsonElement element : jsonObject.get("ingredients").getAsJsonArray()) {
            ingredients.add(new TradeItem(element.getAsJsonObject()));
        }
    }

    public MerchantRecipe generate() {
        MerchantRecipe recipe = new MerchantRecipe(result.generate(), maxUses.generate());

        for (TradeItem item : this.ingredients) {
            recipe.addIngredient(item.generate());
        }

        return recipe;
    }

    public int getWeight() {
        return weight;
    }

    public boolean doesAllowDuplicates() {
        return allowDuplicates;
    }
}
