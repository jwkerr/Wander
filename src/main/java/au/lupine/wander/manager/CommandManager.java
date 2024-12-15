package au.lupine.wander.manager;

import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class CommandManager {

    private static CommandManager instance;

    private CommandManager() {}

    public static CommandManager getInstance() {
        if (instance == null) instance = new CommandManager();
        return instance;
    }

    public void initialise() {
        CommandAPICommand reload = new CommandAPICommand("reload")
                .withPermission("wander.command.wander.reload")
                .executes((sender, args) -> {
                    RecipeManager.getInstance().reload();
                    sender.sendMessage(Component.text("Successfully reloaded Wander", NamedTextColor.GREEN));
                });

        CommandAPICommand command = new CommandAPICommand("wander")
                .withSubcommand(reload);

        command.register();
    }
}
