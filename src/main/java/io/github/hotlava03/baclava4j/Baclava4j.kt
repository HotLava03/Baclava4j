package io.github.hotlava03.baclava4j

import discord4j.core.DiscordClient
import discord4j.core.DiscordClientBuilder
import discord4j.core.event.domain.message.MessageCreateEvent
import io.github.hotlava03.baclava4j.commands.CommandHandler
import io.github.hotlava03.baclava4j.commands.owner.OwnerCategory

object Baclava4j {
    private lateinit var client: DiscordClient
    private lateinit var handler: CommandHandler
    @JvmStatic
    fun main(args: Array<String>) {
        client = DiscordClientBuilder(System.getenv("TOKEN")).build()
        handler = CommandHandler()
        registerCommands(handler) // add all commands to a list
        listenToMessages() // start the message listener
        println("[Init]|[Info] Loaded successfully")
        client.login().block() // nothing past this line will be executed
    }

    private fun registerCommands(handler: CommandHandler) {
        handler.register(OwnerCategory().commands)
    }

    private fun listenToMessages() {
        client.eventDispatcher.on(MessageCreateEvent::class.java)
                .filter { e: MessageCreateEvent -> e.message.content.orElse("").startsWith(">>") }
                .filter { e: MessageCreateEvent -> !e.message.author.get().isBot }
                .filter { e: MessageCreateEvent -> handler.getCommandByName(e.message.content.get().substring(2).split("\\s").toTypedArray()[0]) != null }
                .subscribe({ e: MessageCreateEvent -> handler.execute(e) }) { t: Throwable -> notifyAndReListen(t) } // on error, run the listener again
    }

    private fun notifyAndReListen(e: Throwable) {
        println("-----------------------------------------------------\n"
                + "[Runtime]|[Error] Came across an exception during runtime:")
        e.printStackTrace()
        println("-----------------------------------------------------")
        listenToMessages()
    }
}