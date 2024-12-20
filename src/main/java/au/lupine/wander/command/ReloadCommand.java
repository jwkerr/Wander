package au.lupine.wander.command;

import au.lupine.wander.Wander;
import au.lupine.wander.manager.TradeManager;
import com.google.gson.JsonParseException;
import dev.jorel.commandapi.executors.CommandArguments;
import dev.jorel.commandapi.executors.CommandExecutor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    @Override
    public void run(CommandSender sender, CommandArguments args) {
        try {
            TradeManager.getInstance().reload();
            sender.sendMessage(Component.text("Successfully reloaded Wander", NamedTextColor.GREEN));
        } catch (JsonParseException e) {
            sender.sendMessage(Component.text("Failed to reload Wander, there was an error parsing the JSON, check console for more information", NamedTextColor.RED));

            Wander.logWarning("An error occurred while parsing trades.json: " + e.getMessage());
            for (StackTraceElement trace : e.getStackTrace()) {
                Wander.logWarning(trace.toString());
            }
        }
    }
}
