package com.evasive.me.supernodes.config;

import com.evasive.me.supernodes.SuperNodes;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class NodeConfig {

    public SuperNodes plugin;

    public NodeConfig(SuperNodes plugin) {
        this.plugin = plugin;
    }

    private static File file;
    private static FileConfiguration nodefile;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SuperNodes").getDataFolder(), "nodes.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                new NodeSetup(SuperNodes.getPlugin(SuperNodes.class)).NodeConfigSetup();
            } catch (IOException e) {

            }

        }
        nodefile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return nodefile;
    }

    public static void save() {
        try {
            nodefile.save(file);
        } catch (IOException e) {

        }

    }

    public static void reload() {
        nodefile = YamlConfiguration.loadConfiguration(file);
    }

}