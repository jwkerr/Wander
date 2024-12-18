package au.lupine.wander.manager;

import au.lupine.wander.command.ReloadCommand;
import dev.jorel.commandapi.CommandAPICommand;

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
                .executes(new ReloadCommand());

        CommandAPICommand command = new CommandAPICommand("wander")
                .withSubcommand(reload);

        command.register();
    }
}
