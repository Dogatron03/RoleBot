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
            e.reply("Usage: !roleapply <name>");
            return;
        }
        Role r = Bot.guild.getRolesByName(e.getCommandArgs(), true).stream().findFirst().orElse(null);
        if (r == null) {
            e.reply("Invalid Role!");
            return;
        }
        if (r.canInteract(Bot.guild.getRoleById(Bot.c.getLong("botRole", 0)))) {
            e.reply("Role higher than bot role therefore cannot assign!");
            return;
        }
        User m = e.getMember().getUser();
        Member mb = e.getMember();
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
