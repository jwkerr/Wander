package au.lupine.wander.object.number;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Random;

public class VariableDouble extends VariableNumber<Double> {

    public VariableDouble(Double lower, Double upper) {
        super(lower, upper);
    }

    public static VariableDouble fromJSONElement(JsonElement jsonElement) {
        try {
            return new VariableDouble(jsonElement.getAsDouble(), jsonElement.getAsDouble());
        } catch (Exception e) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            double min = jsonObject.get("min").getAsDouble();
            return new VariableDouble(min, jsonObject.has("max") ? jsonObject.get("max").getAsDouble() : min);
        }
    }

    @Override
    public Double generate() {
        return new Random().nextDouble(lower, upper);
    }
}
