package net.dogatron03.bot.commands;

import net.dogatron03.bot.Bot;
import net.dogatron03.bot.api.CommandCalledEvent;
import net.dogatron03.bot.api.DiscordCommand;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

import java.util.Arrays;

public class RoleApply extends DiscordCommand {

    public RoleApply() {
        super("roleapply", "setrole");
    }

    public void onCommandCalled(CommandCalledEvent e) {
        if (e.getCommandArgs() == null || e.getCommandArgs().equalsIgnoreCase("")) {
            e.reply("Usage: !roleapply <user> <roleid>");
            return;
        }
        String[] x = e.getCommandArgs().split(" ");
        if (x.length < 2) {
            e.reply("Usage: !roleapply <user> <roleid>");
            return;
        }
        User m = e.getMessage().getMentionedUsers().stream().findFirst().orElse(null);
        if (m == null) {
            e.reply("Invalid User!");
            return;
        }
        Role r = Bot.guild.getRoleById(x[1]);
        if (r == null) {
            e.reply("Invalid Role!");
            return;
        }
        if (r.canInteract(Bot.guild.getRoleById(Bot.c.getLong("botRole", 0)))) {
            e.reply("Role higher than bot role therefore cannot assign!");
            return;
        }
        Member mb = Bot.guild.getMember(m);
        if (Bot.c.contains("role." + m.getName())) {
            Role z = Bot.guild.getRoleById(Bot.c.getLong("role." + m.getName()));
            if (!mb.getRoles().contains(r) && mb.getRoles().contains(z)) {
                Bot.guild.getController().modifyMemberRoles(mb, Arrays.asList(new Role[]{r}), Arrays.asList(new Role[]{z})).complete();
            } else if (mb.getRoles().contains(z)) {
                Bot.guild.getController().removeSingleRoleFromMember(mb, z).complete();
            }
        }
        if (!mb.getRoles().contains(r)) {
            Bot.guild.getController().addSingleRoleToMember(mb, r).complete();
        }
        Bot.c.set("role." + m.getName(), r.getIdLong());
        Bot.save();
        e.reply("Added Role to User!");
    }
}
