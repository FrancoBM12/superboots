package com.francobm.superboots.utils;

import com.francobm.superboots.SuperBoots;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileManager extends YamlConfiguration {

    private static FileManager messages;

    public static FileManager getMessages(){
        if (messages == null){
            messages = new FileManager();
        }
        return messages;
    }
    private final SuperBoots plugin;
    private final File messagesFile;

    public FileManager(){
        plugin = SuperBoots.getPlugin(SuperBoots.class);
        messagesFile = new File(plugin.getDataFolder(), "config.yml");
        saveDefault();
        reload();
    }

    public void reload() {
        try {
            super.load(messagesFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            super.save(messagesFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDefault() {
        plugin.saveResource("config.yml", false);
    }
}