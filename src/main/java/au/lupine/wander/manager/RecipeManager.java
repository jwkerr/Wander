package au.lupine.wander.manager;

import com.google.gson.JsonObject;
import au.lupine.wander.Wander;
import au.lupine.wander.object.recipe.TradeSchema;
import au.lupine.wander.util.FileUtil;

import java.io.InputStream;

public class RecipeManager {

    private static RecipeManager instance;

    private TradeSchema schema;

    private RecipeManager() {}

    public static RecipeManager getInstance() {
        if (instance == null) instance = new RecipeManager();
        return instance;
    }

    public void initialise() {
        reload();
    }

    public void reload() {
        saveTradesSchema();
        loadTradesSchema();
    }

    private void saveTradesSchema() {
        InputStream is = Wander.getInstance().getResource("trades.json");
        if (is == null) return;

        try {
            byte[] bytes = is.readAllBytes();
            FileUtil.saveBytesToFile(bytes, FileUtil.CONFIG_DIRECTORY, "trades.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTradesSchema() {
        JsonObject schema = (JsonObject) FileUtil.loadJsonElementFromFile(FileUtil.CONFIG_DIRECTORY, "trades.json");
        if (schema == null) return;

        this.schema = new TradeSchema(schema);
    }

    public TradeSchema getSchema() {
        return schema;
    }
}
