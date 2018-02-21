package net.dogatron03.bot.commands;

import net.dogatron03.bot.Bot;
import net.dogatron03.bot.api.CommandCalledEvent;
import net.dogatron03.bot.api.DiscordCommand;
import net.dv8tion.jda.core.entities.Role;

import java.util.ArrayList;
import java.util.List;

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
        Role r = Bot.guild.getController().createRole().setName(e.getCommandArgs()).setMentionable(true).setPermissions(0).complete();
        Bot.guild.getController().addSingleRoleToMember(e.getMember(), r).complete();
        Bot.c.set("role."+e.getMember().getUser().getName(), r.getIdLong());
        List<Long> x = Bot.c.getLongList(e.getAuthor().getName()+".allowedRoles");
        if(x == null) x = new ArrayList<>();
        x.add(r.getIdLong());
        Bot.c.set(e.getAuthor().getName()+".allowedRoles", x);
        Bot.save();
        e.reply("Created Role!");
    }

}
