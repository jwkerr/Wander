package au.lupine.wander;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import au.lupine.wander.listener.WanderingTraderListener;
import au.lupine.wander.manager.CommandManager;
import au.lupine.wander.manager.RecipeManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Wander extends JavaPlugin {

    private static Wander instance;

    @Override
    public void onEnable() {
        CommandAPI.onEnable();

        CommandManager.getInstance().initialise();
        RecipeManager.getInstance().initialise();

        registerListeners(
                new WanderingTraderListener()
        );
    }

    @Override
    public void onLoad() {
        instance = this;

        CommandAPIBukkitConfig config = new CommandAPIBukkitConfig(this);
        CommandAPI.onLoad(config);
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public static Wander getInstance() {
        return instance;
    }

    public static void logInfo(String msg) {
        instance.getLogger().info(msg);
    }

    public static void logWarning(String msg) {
        instance.getLogger().warning(msg);
    }

    public static void logSevere(String msg) {
        instance.getLogger().severe(msg);
    }
}
