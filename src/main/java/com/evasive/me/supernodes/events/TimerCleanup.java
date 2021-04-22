package com.evasive.me.supernodes.events;

import com.evasive.me.supernodes.SuperNodes;
import com.evasive.me.supernodes.config.NodeConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class TimerCleanup {

    int taskID = 0;

    public SuperNodes plugin;

    public TimerCleanup(SuperNodes plugin) {
        this.plugin = plugin;
    }

    HashMap<Integer, String> materiallist = new HashMap<Integer, String>();
    Material ori = Material.AIR;

    public void cleanTimer() {
        for (Map.Entry<String, Map<String, Integer>> entry : CustomBlockEvents.timer.entrySet()) {
            for (Map.Entry<String, Integer> entry2 : entry.getValue().entrySet()) {
                String cordlist = entry2.getKey();
                String[] cords = cordlist.split(",");
                String worldsplit = entry.getKey();
                String[] worldname1 = worldsplit.split("=");
                String[] worldname2 = worldname1[1].split("}");
                int x = Integer.parseInt(cords[0]);
                int y = Integer.parseInt(cords[1]);
                int z = Integer.parseInt(cords[2]);
                String wrld = entry.getKey();
                World world = Bukkit.getWorld(worldname2[0]);
                Location location = new Location(world, x, y, z);
                FileConfiguration nodes = NodeConfig.get();
                if (nodes != null) {
                    if (nodes.getConfigurationSection("Nodes") != null) {
                        if (CustomBlockEvents.customBlockManager.isCustomBlock(entry.getKey(), cordlist)) {//Fix with new world input

                            String hold = entry2.getKey();
                            taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SuperNodes.getPlugin(SuperNodes.class), new TimerTask() {
                                @Override
                                public void run() {
                                    int number = CustomBlockEvents.timer.get(wrld).get(hold);
                                    if (number == 0) {

                                        Bukkit.getScheduler().cancelTask(CustomBlockEvents.table.get(cordlist));
                                        ori = Material.getMaterial(NodeConfig.get().getString("Nodes." + CustomBlockEvents.customBlockManager.getCustomBlockName(wrld, cordlist) + ".BlockMaterial"));
                                        location.getBlock().setType(ori);
                                    } else {
                                        CustomBlockEvents.timer.get(wrld).put(hold, number - 1);
                                    }
                                }
                            }, 20, 20);
                            CustomBlockEvents.table.put(hold, taskID);
/*                                } else {
                                    location.getBlock().setType(ori);
                                }*/
                        }
                        //if (CustomBlockEvents.timer.get(entry).get(entry2.getKey()) > 0) {
                    }
                }
            }
        }
    }
}