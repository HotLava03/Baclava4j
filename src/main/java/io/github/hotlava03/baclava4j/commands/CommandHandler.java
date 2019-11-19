package io.github.hotlava03.baclava4j.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import discord4j.core.event.domain.message.MessageCreateEvent;

public class CommandHandler {

    private List<Command> commands = new ArrayList<>();

    public List<Command> getCommands() {
        return this.commands;
    }

    public void register(Command... commands) {
        register(Arrays.asList(commands));
    }

    public void register(List<Command> commands) {
        this.commands.addAll(commands);
    }

    public Command getCommandByName(String name) {
        for (Command cmd : commands) {
            if (cmd.getName().equalsIgnoreCase(name))
                return cmd;
            for (String alias : cmd.getAliases())
                if (alias.equalsIgnoreCase(name))
                    return cmd;    
        }
        return null;
    }

    public void execute(MessageCreateEvent e) {
        Command command = this.getCommandByName(e.getMessage().getContent().get().substring(2).split("\\s")[0]);
        command.getExecutor().onCommand(new CommandEvent(e));
    }
}