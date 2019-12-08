package io.github.hotlava03.baclava4j.commands

import discord4j.core.DiscordClient
import discord4j.core.`object`.entity.Guild
import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.util.Snowflake
import discord4j.core.event.domain.message.MessageCreateEvent

class CommandEvent(e: MessageCreateEvent) {
    val client: DiscordClient = e.client
    val guild: Guild = e.guild.block()!!
    val guildId: Snowflake = e.guildId.get()
    val member: Member = e.member.get()
    val message: Message = e.message
    val channel: MessageChannel = message.channel.block()!!
    var args: Array<String>
    var argsRaw: String

    init {
        val fullArgs = message.content.orElse("").substring(2)
        if (fullArgs.split("\\s").size == 1)
            argsRaw = fullArgs.replaceFirst(fullArgs.split(" ")[0], "")
        else
            argsRaw = fullArgs.replaceFirst(fullArgs.split(" ")[0] + " ", "")
        args = argsRaw.split("\\s").toTypedArray()
        if (args[0].isEmpty()) args = arrayOf()
    }

    fun reply(txt: CharSequence) {
        channel.createMessage(txt.toString()).subscribe()
    }
}