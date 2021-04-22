package com.evasive.me.supernodes.config;

import com.evasive.me.supernodes.SuperNodes;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class TimerConfig {

    public SuperNodes plugin;

    public TimerConfig(SuperNodes plugin) {
        this.plugin = plugin;
    }

    private static File file;
    private static FileConfiguration timerfile;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SuperNodes").getDataFolder(), "data/timerfile.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {

            }

        }
        timerfile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return timerfile;
    }

    public static void save() {
        try {
            timerfile.save(file);
        } catch (IOException e) {

        }

    }

    public static void reload() {
        timerfile = YamlConfiguration.loadConfiguration(file);
    }

}