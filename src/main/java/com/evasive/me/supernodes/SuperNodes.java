package com.evasive.me.supernodes;

import com.evasive.me.supernodes.commands.NodeCommands;
import com.evasive.me.supernodes.commands.NodeCommandsTabComplete;
import com.evasive.me.supernodes.config.LocationConfig;
import com.evasive.me.supernodes.config.NodeConfig;
import com.evasive.me.supernodes.config.TimerConfig;
import com.evasive.me.supernodes.config.TimerSetup;
import com.evasive.me.supernodes.events.CustomBlockEvents;
import com.evasive.me.supernodes.events.TimeCheckEvent;
import com.evasive.me.supernodes.events.TimerCleanup;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class SuperNodes extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CustomBlockEvents(this), this);
        this.saveDefaultConfig();
        NodeConfig.setup();
        LocationConfig.setup();
        TimerConfig.setup();
        TimerSetup timerSetup = new TimerSetup(this);
        TimerCleanup timerCleanup = new TimerCleanup(this);
        if (TimerConfig.get().contains("Times")) {
            timerSetup.getTime();
        }
        CustomBlockEvents.customBlockManager.loadWorldData();
        timerCleanup.cleanTimer();
        LocationConfig.get().set("Location", null);
        LocationConfig.save();
        getServer().getPluginManager().registerEvents(new TimeCheckEvent(this), this);
        new NodeCommands(this);
        new NodeCommandsTabComplete(this);
        getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "[Nodes] has successfully started");
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        TimerSetup timerSetup = new TimerSetup(this);
        if (!CustomBlockEvents.timer.isEmpty()) {
            timerSetup.saveLoc();
        }
        CustomBlockEvents.customBlockManager.saveWorldData();
        getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "[Nodes] has successfully shutdown");
        // Plugin shutdown logic
    }
}
