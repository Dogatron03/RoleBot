package net.dogatron03.bot.commands;

import net.dogatron03.bot.Bot;
import net.dogatron03.bot.api.CommandCalledEvent;
import net.dogatron03.bot.api.DiscordCommand;
import net.dv8tion.jda.core.entities.Role;

public class RoleDelete extends DiscordCommand {

    public RoleDelete() {
        super("roledelete", "delete");
    }

    public void onCommandCalled(CommandCalledEvent e) {
        if(e.getCommandArgs() == null || e.getCommandArgs().equalsIgnoreCase("")){
            e.reply("Usage: !delete <id>");
            return;
        }
        if(Bot.guild.getRoleById(e.getCommandArgs()) == null){
            e.reply("Role doesn't exist!");
            return;
        }
        Role r = Bot.guild.getRoleById(e.getCommandArgs());
        if(r.canInteract(Bot.guild.getRoleById(Bot.c.getLong("botRole", 0)))){
            e.reply("Role higher than bot role therefore cannot assign!");
            return;
        }
        r.delete().complete();
        e.reply("Deleted Role!");
    }

}
