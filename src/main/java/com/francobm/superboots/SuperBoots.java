package com.francobm.superboots;

import com.francobm.superboots.commands.Command;
import com.francobm.superboots.files.FileCreator;
import com.francobm.superboots.listeners.Boots;
import com.francobm.superboots.managers.checkBoots;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SuperBoots extends JavaPlugin {

    private FileCreator config;
    public String prefix;
    @Override
    public void onEnable() {
        // Plugin startup logic
        config = new FileCreator(this, "config");
        prefix = config.getString("messages.prefix");
        registerCommands();
        registerListener();
        new checkBoots(this);
    }

    public void registerListener(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new Boots(), this);
    }

    public void registerCommands(){
        getCommand("superboots").setExecutor(new Command(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public FileCreator getConfig() {
        return config;
    }
}
