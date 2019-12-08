package io.github.hotlava03.baclava4j.commands.owner

import discord4j.core.`object`.entity.PrivateChannel
import discord4j.core.`object`.reaction.ReactionEmoji
import io.github.hotlava03.baclava4j.commands.CommandEvent
import io.github.hotlava03.baclava4j.commands.CommandExecutor
import io.github.hotlava03.baclava4j.components.EvalResult
import io.github.hotlava03.baclava4j.components.JavaEvalComponent.eval

class JEval : CommandExecutor() {
    override fun onCommand(e: CommandEvent?) {
        if (e!!.member.id.asString() != "362753440801095681") return
        if (e.args.isEmpty()) {
            e.reply("You dum dum add some code")
            return
        }
        val shortcuts: MutableMap<String?, Any?> = HashMap()
        shortcuts["e"] = e
        shortcuts["event"] = e
        shortcuts["client"] = e.client
        shortcuts["guild"] = e.guild
        shortcuts["channel"] = e.channel
        shortcuts["guildId"] = e.guildId
        shortcuts["member"] = e.member
        shortcuts["author"] = e.member
        shortcuts["message"] = e.message
        val result = eval(e.argsRaw, shortcuts)
        if (result is EvalResult) {
            e.message.addReaction(ReactionEmoji.unicode("\u2705")).subscribe()
            if (result.isEmpty) return
            e.reply("```" + result.result + "```")
        } else {
            e.message.addReaction(ReactionEmoji.unicode("\u274C")).subscribe()
            e.member.privateChannel.subscribe { c: PrivateChannel -> c.createMessage("Eval error:\n$result").subscribe() }
        }
    }
}