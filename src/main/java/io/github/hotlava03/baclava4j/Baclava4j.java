package io.github.hotlava03.baclava4j;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.message.MessageCreateEvent;
import io.github.hotlava03.baclava4j.commands.CommandHandler;
import io.github.hotlava03.baclava4j.commands.owner.OwnerCategory;

public class Baclava4j {

    private static DiscordClient client;
    private static CommandHandler handler;

    public static void main(String[] args) {
        client = new DiscordClientBuilder(System.getenv("TOKEN")).build();
        handler = new CommandHandler();
        registerCommands(handler); // add all commands to a list
        listenToMessages(); // start the message listener
        System.out.println("[Init]|[Info] Loaded successfully"); 
        client.login().block(); // nothing past this line will be executed     
    }

    private static void registerCommands(CommandHandler handler) {
        handler.register(new OwnerCategory().getCommands());
    }

    private static void listenToMessages() {
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .filter(e -> e.getMessage().getContent().orElse("").startsWith(">>"))
                .filter(e -> !e.getMessage().getAuthor().get().isBot())
                .filter(e -> handler.getCommandByName(e.getMessage().getContent().get().substring(2).split("\\s")[0]) != null)
                .subscribe(handler::execute, e -> notifyAndReListen(e)); // on error, run the listener again
    }

    private static void notifyAndReListen(Throwable e) {
        System.out.println("-----------------------------------------------------\n"
                + "[Runtime]|[Error] Came across an exception during runtime:");
        e.printStackTrace();
        System.out.println("-----------------------------------------------------");
        listenToMessages();
    }
}