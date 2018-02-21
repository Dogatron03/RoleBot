package net.dogatron03.bot.api;

public class MessageCommand extends DiscordCommand {
    private String message;

    public MessageCommand(String name, String message) {
        super(name);
        this.message = message;
    }

    public MessageCommand(String name, String message, String... aliases) {
        super(name, aliases);
        this.message = message;
    }

    public void onCommandCalled(CommandCalledEvent e) {
        if (!e.getAuthor().isBot()) {
            e.reply(message);
        }
    }
}
