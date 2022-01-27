package com.francobm.superboots;

import com.francobm.superboots.commands.Command;
import com.francobm.superboots.listeners.Boots;
import com.francobm.superboots.managers.checkBoots;
import com.francobm.superboots.utils.FileManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SuperBoots extends JavaPlugin {

    private final FileManager config;
    public String prefix;

    public SuperBoots(FileManager config) {
        this.config = config;
    }

    @Override
    public void onEnable() {
        prefix = config.getString("messages.prefix");
        FileManager.getMessages();
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
        getLogger().info("SuperBoots has been disabled");
    }

    public static SuperBoots instace;

    public static SuperBoots getInstace() {
        return instace;
    }

}
