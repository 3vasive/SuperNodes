package com.evasive.me.supernodes.commands;

import com.evasive.me.supernodes.SuperNodes;
import com.evasive.me.supernodes.config.NodeConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class NodeCommandsTabComplete implements TabCompleter {
    public SuperNodes plugin;

    public NodeCommandsTabComplete(SuperNodes plugin) {
        this.plugin = plugin;
        plugin.getCommand("nodes").setTabCompleter(this);
    }

    List<String> arguments1 = new ArrayList<String>();
    List<String> arguments2 = new ArrayList<String>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (arguments1.isEmpty()) {
            arguments1.add("give");
            arguments1.add("reload");
        }
        List<String> result = new ArrayList<String>();
        if (args.length == 1) {
            for (String a : arguments1) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(a);
            }
            arguments1.clear();
            return result;
        } else if (args.length == 2) {
            if (args[0].equals("give")) {
                if (arguments2.isEmpty()) {
                    NodeConfig.get().getConfigurationSection("Nodes").getKeys(false).forEach(area -> {
                        arguments2.add(area);
                    });
                }
            }
            result.clear();
            for (String a : arguments2) {
                result.add(a);
            }
            arguments2.clear();
            return result;
        }
        return null;
    }
}
