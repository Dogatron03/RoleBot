package net.dogatron03.bot.commands;

import net.dogatron03.bot.Bot;
import net.dogatron03.bot.api.CommandCalledEvent;
import net.dogatron03.bot.api.DiscordCommand;

public class RoleNew extends DiscordCommand {

    public RoleNew() {
        super("rolenew", "addrole");
    }

    public void onCommandCalled(CommandCalledEvent e) {
        if(e.getCommandArgs() == null || e.getCommandArgs().equalsIgnoreCase("")){
            e.reply("Usage: !addrole <Name>");
            return;
        }
        if(Bot.guild.getRolesByName(e.getCommandArgs(), true).stream().findFirst().orElse(null) != null){
            e.reply("Role already exists!");
            return;
        }
        Bot.guild.getController().createRole().setName(e.getCommandArgs()).setMentionable(true).setPermissions(0).complete();
        e.reply("Created Role!");
    }

}