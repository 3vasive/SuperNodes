package com.evasive.me.supernodes.config;

import com.evasive.me.supernodes.SuperNodes;
import com.evasive.me.supernodes.events.CustomBlockEvents;

import java.util.HashMap;
import java.util.Map;

public class TimerSetup {

    public SuperNodes plugin;

    public TimerSetup(SuperNodes plugin) {
        this.plugin = plugin;
    }

    public void saveLoc() {
        for (Map.Entry<String, Map<String, Integer>> entry : CustomBlockEvents.timer.entrySet()) {
            for (Map.Entry<String, Integer> entry2 : entry.getValue().entrySet()) {
                TimerConfig.get().set("Times." + entry.getKey() + "." + entry2.getKey(), entry2.getValue());
            }
        }
        TimerConfig.save();
    }

    public void getTime() {
        TimerConfig.get().getConfigurationSection("Times").getKeys(false).forEach(key -> {
            if (!CustomBlockEvents.timer.containsKey(key)) {
                CustomBlockEvents.timer.put(key, new HashMap<>());
            }
            TimerConfig.get().getConfigurationSection("Times." + key).getKeys(false).forEach(key2 -> {
                CustomBlockEvents.timer.get(key).put(key2, TimerConfig.get().getInt("Times." + key + "." + key2));
            });
            TimerConfig.save();
        });
        for (String key : TimerConfig.get().getKeys(false)) {
            TimerConfig.get().set(key, null);
        }
        TimerConfig.save();
    }

}