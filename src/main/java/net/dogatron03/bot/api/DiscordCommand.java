package net.dogatron03.bot.api;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.PrivateChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DiscordCommand {

    private static List<DiscordCommand> commands = new LinkedList<>();
    private List<String> aliases;
    private long channel;
    private String name;


    public DiscordCommand(String name, long channel, String... aliases) {
        commands.add(this);
        this.aliases = aliases != null ? Arrays.asList(aliases) : new ArrayList<>();
        this.name = name;
        this.channel = channel;
    }

    public DiscordCommand(String name, String... aliases) {
        commands.add(this);
        this.aliases = aliases != null ? Arrays.asList(aliases) : new ArrayList<>();
        this.name = name;
        this.channel = 0L;
    }

    public static void onMessageRecived(CommandCalledEvent e) {
        DiscordCommand c = commands.stream().filter(f -> f.name.equalsIgnoreCase(e.getCommandName()) || f.aliases.stream().anyMatch(x -> x.equalsIgnoreCase(e.getCommandName()))).findFirst().orElse(null);
        if(c == null) return;
        if (c.channel != 0 && e.getMessage().getChannel().getIdLong() != c.channel && !e.isFromType(ChannelType.PRIVATE)) {
            PrivateChannel ch = e.getAuthor().openPrivateChannel().complete();
            ch.sendMessage("Wrong channel for that command").complete();
            ch.close().complete();
            e.getMessage().delete().complete();
        } else {
            c.onCommandCalled(e);
        }
    }

    public static List<DiscordCommand> getCommands() {
        return commands;
    }

    public long getChannel() {
        return channel;
    }

    public void setChannel(long channel) {
        this.channel = channel;
    }

    public void onCommandCalled(CommandCalledEvent e) {
        e.reply("Something went wrong!");
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public List<String> getAliases() {
        return aliases;
    }
}
