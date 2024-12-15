package au.lupine.wander.object.number;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Random;

public class VariableInteger extends VariableNumber<Integer> {

    public VariableInteger(Integer lower, Integer upper) {
        super(lower, upper);
    }

    public static VariableInteger fromJSONElement(JsonElement jsonElement) {
        try {
            return new VariableInteger(jsonElement.getAsInt(), jsonElement.getAsInt());
        } catch (Exception e) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            int min = jsonObject.get("min").getAsInt();
            return new VariableInteger(min, jsonObject.has("max") ? jsonObject.get("max").getAsInt() : min);
        }
    }

    @Override
    public Integer generate() {
        return new Random().nextInt(lower, upper + 1);
    }
}
