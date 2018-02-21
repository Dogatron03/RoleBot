package net.dogatron03.bot.commands;

import net.dogatron03.bot.Bot;
import net.dogatron03.bot.api.CommandCalledEvent;
import net.dogatron03.bot.api.DiscordCommand;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Role;

import java.util.List;

public class RoleDisAllow extends DiscordCommand {
    public RoleDisAllow() {
        super("roledisallow", 389889650749210624L, "disallowrole");
    }

    public void onCommandCalled(CommandCalledEvent e) {
        if (e.getChannelType() == ChannelType.PRIVATE) return;
        if (e.getCommandArgs() == null || e.getCommandArgs().equalsIgnoreCase("")) {
            e.reply("Usage: !roledisallow <name>");
            return;
        }
        Role r = Bot.guild.getRolesByName(e.getCommandArgs(), true).stream().findFirst().orElse(null);
        if (r == null) {
            e.reply("Invalid Role!");
            return;
        }
        List<Long> x = Bot.c.getLongList("allowedRoles");
        if (x.contains(r.getIdLong()))
            x.remove(r.getIdLong());
        Bot.c.set("allowedRoles", x);
        Bot.save();
        e.reply("Disallowed Role!");
    }


}
