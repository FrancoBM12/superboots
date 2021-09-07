package com.francobm.specialboots;

import com.francobm.specialboots.commands.Command;
import com.francobm.specialboots.listeners.Boots;
import com.francobm.specialboots.managers.checkBoots;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpecialBoots extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerCommands();
        registerListener();
        new checkBoots(this);
    }

    public void registerListener(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new Boots(), this);
    }

    public void registerCommands(){
        getCommand("specialboots").setExecutor(new Command());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
