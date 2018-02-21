package net.dogatron03.bot.api;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return;
        if (e.isFromType(ChannelType.TEXT) || e.isFromType(ChannelType.PRIVATE))
            CommandCalledEvent.onMessageRecived(e.getJDA(), e.getResponseNumber(), e.getMessage());
    }

}
