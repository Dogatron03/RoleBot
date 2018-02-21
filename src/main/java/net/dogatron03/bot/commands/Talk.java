package net.dogatron03.bot.commands;

import net.dogatron03.bot.Bot;
import net.dogatron03.bot.api.CommandCalledEvent;
import net.dogatron03.bot.api.DiscordCommand;

public class Talk extends DiscordCommand {
    public Talk() {
        super("talk");
    }


    public void onCommandCalled(CommandCalledEvent e) {
        Bot.guild.getTextChannelById(Bot.c.getLong("channel", 0)).sendMessage(e.getCommandArgs()).complete();
    }

}
