package io.github.hotlava03.baclava4j.commands

import discord4j.core.event.domain.message.MessageCreateEvent

class CommandHandler {
    private val commands: MutableList<Command> = ArrayList()
    fun getCommands(): List<Command> {
        return commands
    }

    fun register(vararg commands: Command) {
        register(listOf(*commands))
    }

    fun register(commands: List<Command>) {
        this.commands.addAll(commands)
    }

    fun getCommandByName(name: String): Command? {
        for (cmd in commands) {
            if (cmd.name.equals(name, ignoreCase = true)) return cmd
            for (alias in cmd.aliases ?: arrayOf()) if (alias.equals(name, ignoreCase = true)) return cmd
        }
        return null
    }

    fun execute(e: MessageCreateEvent) {
        val command = getCommandByName(e.message.content.get().substring(2).split("\\s").toTypedArray()[0])!!
        command.executor.onCommand(CommandEvent(e))
    }
}