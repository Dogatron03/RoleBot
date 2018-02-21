package net.dogatron03.bot.commands;

import net.dogatron03.bot.Bot;
import net.dogatron03.bot.api.CommandCalledEvent;
import net.dogatron03.bot.api.DiscordCommand;
import net.dv8tion.jda.core.entities.Role;

import java.awt.*;

public class RoleColourEdit extends DiscordCommand {

    public RoleColourEdit() {
        super("rolecolour", "colour");
    }


    public void onCommandCalled(CommandCalledEvent e) {
        if(e.getCommandArgs() == null || e.getCommandArgs().equalsIgnoreCase("")){
            e.reply("Usage: !rolecolour <red> <green> <blue>");
            return;
        }
        String[] x = e.getCommandArgs().split(" ");
        if(x.length < 3) {
            e.reply("Usage: !rolecolour <red> <green> <blue>");
            return;
        }
        int r;
        int g;
        int b;
        try {
            r = Integer.parseInt(x[0]);
            g = Integer.parseInt(x[1]);
            b = Integer.parseInt(x[2]);
        } catch (NumberFormatException ex){
            e.reply("Invalid Number!");
            return;
        }
        if(!Bot.c.contains("role."+e.getAuthor().getName())){
            e.reply("User not assigned a role!");
            return;
        }
        Role y = Bot.bot.getRoleById(Bot.c.getLong("role."+e.getAuthor().getName()));
        if(y == null) {
            e.reply("Invalid Role for User!");
            return;
        }
        y.getManager().setColor(new Color(r, g, b)).complete();
        e.reply("Set User's Colour!");
    }
}
