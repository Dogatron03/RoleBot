package net.dogatron03.bot;

import net.dogatron03.bot.api.EventListener;
import net.dogatron03.bot.api.MessageCommand;
import net.dogatron03.bot.commands.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;


public class Bot extends Plugin {

    public static Guild guild;
    public static JDA bot;
    public static Bot plugin;
    public static Configuration c;
    public static File f;

    public void onEnable() {
        plugin = this;
        f = new File(getDataFolder(), "config.yml");
        try {
            getDataFolder().mkdir();
            if(!f.exists()) f.createNewFile();
            c = ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);
            bot = new JDABuilder(AccountType.BOT).setToken(c.getString("token", "lolGoodToken")).buildBlocking();
            bot.addEventListener(new EventListener());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        guild = bot.getGuildById(c.getLong("guild", 0));


        guild.getTextChannelById(c.getLong("channel", 0)).sendMessage(c.getString("bootMessage", "Hello World! Somebody hasn't provided me with a boot message!")).complete();

        new RoleColourEdit();
        new RoleApply();
        new RoleNew();
        new RoleGet();
        new RoleDelete();

        new MessageCommand("help", c.getString("help", "Lol no help for you ha!"), "rolehelp", "justforretards");
    }

    public static void save(){
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(c, f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

