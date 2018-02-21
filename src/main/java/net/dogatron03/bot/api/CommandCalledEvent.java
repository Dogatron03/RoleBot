package net.dogatron03.bot.api;

import net.dogatron03.bot.Bot;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.GenericMessageEvent;

public class CommandCalledEvent extends GenericMessageEvent {
    private final Message message;
    private final String[] messageRaw;
    private final String commandName;
    private final String args;


    private CommandCalledEvent(JDA api, long responseNumber, Message message) {
        super(api, responseNumber, message.getIdLong(), message.getChannel());
        this.message = message;
        messageRaw = message.getContent().split(" ", 2);
        commandName = messageRaw[0].substring(1);
        if (messageRaw.length == 2) {
            args = messageRaw[1];
        } else args = null;
    }


    public static void onMessageRecived(JDA api, long responseNumber, Message message) {
        if (message.getContent().startsWith("!")) {
            CommandCalledEvent e = new CommandCalledEvent(api, responseNumber, message);
            DiscordCommand.onMessageRecived(e);
        }
    }

    public void reply(String reply) {
        if (isFromType(ChannelType.PRIVATE)) message.getPrivateChannel().sendMessage(reply).complete();
        else message.getChannel().sendMessage(reply).complete();
    }

    public MessageChannel getChannelRaw() {
        return message.getChannel();
    }

    public Message getMessage() {
        return message;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandArgs() {
        return args;
    }

    public User getAuthor() {
        return message.getAuthor();
    }

    public Member getMember() {
        return isFromType(ChannelType.TEXT) ? getGuild().getMember(getAuthor()) : isFromType(ChannelType.PRIVATE) ? Bot.guild.getMember(getAuthor()) : null;
    }

    public boolean isPrivate() {
        return isFromType(ChannelType.PRIVATE);
    }

    public MessageChannel getChannel() {
        return message.getChannel();
    }

    public Guild getGuild() {
        return isFromType(ChannelType.PRIVATE) ? Bot.guild : message.getGuild();
    }


}
