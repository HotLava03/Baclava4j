package io.github.hotlava03.baclava4j.commands;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Mono;

public class CommandEvent {

    private DiscordClient client;
    private Mono<Guild> guild;
    private Snowflake guildId;
    private Member member;
    private Message message;
    private Mono<MessageChannel> channel;
    private String[] args;
    private String argsRaw;

    public CommandEvent(MessageCreateEvent e) {
        this.client = e.getClient();
        this.guild = e.getGuild();
        this.guildId = e.getGuildId().get();
        this.member = e.getMember().get();
        this.message = e.getMessage();
        this.channel = this.message.getChannel();
        String fullArgs = message.getContent().orElse("").substring(2);
        if (fullArgs.split("\\s").length == 1)
            this.argsRaw = fullArgs.replaceFirst(fullArgs.split("\\s")[0], "");
        else
            this.argsRaw = fullArgs.replaceFirst(fullArgs.split("\\s")[0] + " ", "");
        this.args = argsRaw.split("\\s");
        if (this.args[0].isEmpty())
            this.args = new String[] {};
    }

    public void reply(CharSequence txt) {
        channel.subscribe(c -> c.createMessage(txt.toString()).subscribe());
    }

    public DiscordClient getClient() {
        return client;
    }

    public Mono<Guild> getGuild() {
        return guild;
    }

    public Snowflake getGuildId() {
        return guildId;
    }

    public Member getMember() {
        return member;
    }

    public Message getMessage() {
        return message;
    }

    public Mono<MessageChannel> getChannel() {
        return this.channel;
    }

    public String[] getArgs() {
        return this.args;
    }

    public String getArgsRaw() {
        return this.argsRaw;
    }
}