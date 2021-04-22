package com.evasive.me.supernodes.manager;

import com.evasive.me.supernodes.config.LocationConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class CustomBlockManager {

    private final Map<String, Map<String, String>> blockMap;

    public void initWorld(String world) {
        if (!blockMap.containsKey(world)) {
            this.blockMap.put(world, new HashMap<>());
        }
    }

    public CustomBlockManager() {
        this.blockMap = new HashMap<>();
    }

    public boolean isCustomBlock(final String world, final String location) {
        if (blockMap.get(world) == null) {
            return false;
        }
        return this.blockMap.get(world).containsKey(location);
    }

    public boolean addCustomBlock(final String world, final String name, final String location) {
        if (blockMap.get(world).containsKey(location)) {
            return false;
        }
        this.blockMap.get(world).put(location, name);
        return true;
    }

    public String getCustomBlockName(String world, String location) {
        return blockMap.get(world).get(location);
    }

    public void removeCustomBlock(final String world, final String location) {
        if (blockMap.get(world).containsKey(location)) {
            this.blockMap.get(world).remove(location);
        }
    }

    public void saveWorldData() {
        LocationConfig.setup();
        for (Map.Entry<String, Map<String, String>> worldlist : blockMap.entrySet()) {
            for (Map.Entry<String, String> locname : worldlist.getValue().entrySet()) {
                LocationConfig.get().set("Location." + worldlist.getKey() + "." + locname.getKey(), locname.getValue());
            }
        }
        LocationConfig.save();
    }

    public void loadWorldData() {
        FileConfiguration load = LocationConfig.get();
        if (load != null) {
            if (load.getConfigurationSection("Location") != null) {
                load.getConfigurationSection("Location").getKeys(false).forEach(key -> {
                    blockMap.put(key, new HashMap<>());
                    load.getConfigurationSection("Location." + key).getKeys(false).forEach(loc123 -> {
                        String location = loc123;
                        String name = LocationConfig.get().getString("Location." + key + "." + loc123);
                        this.blockMap.get(key).put(location, name);
                    });
                });
                LocationConfig.save();
            }
        }
    }
}