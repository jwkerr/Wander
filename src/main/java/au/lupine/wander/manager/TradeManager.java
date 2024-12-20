package au.lupine.wander.manager;

import com.google.gson.JsonObject;
import au.lupine.wander.Wander;
import au.lupine.wander.object.trade.TradeSchema;
import au.lupine.wander.util.FileUtil;

import java.io.InputStream;

public class TradeManager {

    private static TradeManager instance;

    private TradeSchema schema;

    private TradeManager() {}

    public static TradeManager getInstance() {
        if (instance == null) instance = new TradeManager();
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
            Wander.logWarning("An error occurred while saving the trades schema: " + e.getMessage());
            for (StackTraceElement trace : e.getStackTrace()) {
                Wander.logWarning(trace.toString());
            }
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
