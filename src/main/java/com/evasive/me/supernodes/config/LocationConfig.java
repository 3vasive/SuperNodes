package com.evasive.me.supernodes.config;

import com.evasive.me.supernodes.SuperNodes;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LocationConfig {

    public SuperNodes plugin;

    public LocationConfig(SuperNodes plugin) {
        this.plugin = plugin;
    }

    private static File file;
    private static FileConfiguration locationfile;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SuperNodes").getDataFolder(), "data/locations.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {

            }

        }
        locationfile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return locationfile;
    }

    public static boolean ishere() {
        if (!file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static void save() {
        try {
            locationfile.save(file);
        } catch (IOException e) {

        }

    }

    public static void delete() {
        file.delete();
    }
}