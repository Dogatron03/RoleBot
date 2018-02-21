package net.dogatron03.bot.commands;

import net.dogatron03.bot.Bot;
import net.dogatron03.bot.api.CommandCalledEvent;
import net.dogatron03.bot.api.DiscordCommand;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Role;

import java.util.List;

public class RoleAllow extends DiscordCommand {
    public RoleAllow() {
        super("roleallow", 389889650749210624L,"allowrole");
    }

    public void onCommandCalled(CommandCalledEvent e) {
        if(e.getChannelType() == ChannelType.PRIVATE) return;
        if(e.getCommandArgs() == null || e.getCommandArgs().equalsIgnoreCase("")){
            e.reply("Usage: !roleallow <name>");
            return;
        }
        Role r = Bot.guild.getRolesByName(e.getCommandArgs(), true).stream().findFirst().orElse(null);
        if(r == null){
            e.reply("Invalid Role!");
            return;
        }
        List<Long> x = Bot.c.getLongList("allowedRoles");
        x.add(r.getIdLong());
        Bot.c.set("allowedRoles", x);
        Bot.save();
        e.reply("Allowed Role!");
    }


}
