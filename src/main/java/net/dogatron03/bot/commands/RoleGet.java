package net.dogatron03.bot.commands;

import joptsimple.internal.Strings;
import net.dogatron03.bot.Bot;
import net.dogatron03.bot.api.CommandCalledEvent;
import net.dogatron03.bot.api.DiscordCommand;

import java.util.ArrayList;
import java.util.List;

public class RoleGet extends DiscordCommand {

    public RoleGet(){
        super("roleget", "roles");
    }

    public void onCommandCalled(CommandCalledEvent e) {
        List<String> roles = new ArrayList<>();
        Bot.guild.getRoles().forEach(x -> roles.add(x.getName().replace("@everyone", "Default") + ": " + x.getIdLong()));
        e.reply("Roles in " + e.getGuild().getName() + ": \n" + Strings.join(roles, "\n"));
    }

}
