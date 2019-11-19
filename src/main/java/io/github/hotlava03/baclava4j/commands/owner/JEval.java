package io.github.hotlava03.baclava4j.commands.owner;

import java.util.HashMap;
import java.util.Map;

import discord4j.core.object.reaction.ReactionEmoji;
import io.github.hotlava03.baclava4j.commands.CommandEvent;
import io.github.hotlava03.baclava4j.commands.CommandExecutor;
import io.github.hotlava03.baclava4j.components.EvalComponent;
import io.github.hotlava03.baclava4j.components.EvalResult;

public class JEval extends CommandExecutor {

    @Override
    public void onCommand(CommandEvent e) {
        if (!e.getMember().getId().asString().equals("362753440801095681"))
            return;

        if (e.getArgs().length == 0) {
            e.reply("You dum dum add some code");
            return;
        }    
   
        Map<String, Object> shortcuts = new HashMap<>();
        shortcuts.put("e", e);
        shortcuts.put("event", e);
        shortcuts.put("client", e.getClient());
        shortcuts.put("guild", e.getGuild());
        shortcuts.put("channel", e.getChannel());
        shortcuts.put("guildId", e.getGuildId());
        shortcuts.put("member", e.getMember());
        shortcuts.put("author", e.getMember());
        
        Object result = EvalComponent.eval(e.getArgsRaw(), shortcuts);

        if (result instanceof EvalResult) {
            e.getMessage().addReaction(ReactionEmoji.unicode("\u2705")).subscribe();
            EvalResult eRes = (EvalResult) result;
            if (eRes.isEmpty())
                return;
            e.reply("```" + eRes.getResult() + "```");
        } else {
            e.getMessage().addReaction(ReactionEmoji.unicode("\u274C")).subscribe();
            e.getMember().getPrivateChannel().subscribe(c -> c.createMessage("Eval error:\n" + result.toString()).subscribe());
        }
    }
}