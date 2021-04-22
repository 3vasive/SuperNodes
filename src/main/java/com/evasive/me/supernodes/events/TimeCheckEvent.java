package com.evasive.me.supernodes.events;

import com.evasive.me.supernodes.SuperNodes;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class TimeCheckEvent implements Listener {

    public SuperNodes plugin;

    public TimeCheckEvent(SuperNodes plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void rightClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            EquipmentSlot n = e.getHand();
            if (n.equals(EquipmentSlot.HAND)) {
                Location location = e.getClickedBlock().getLocation();
                int x = location.getBlockX();
                int y = location.getBlockY();
                int z = location.getBlockZ();
                String location123 = ("" + x + "," + y + "," + z);
                String world = String.valueOf(e.getPlayer().getWorld());
                if (CustomBlockEvents.customBlockManager.isCustomBlock(world, location123)) {
                    if (CustomBlockEvents.timer.get(world).get(location123) >= 3600) {
                        Integer hold = CustomBlockEvents.timer.get(world).get(location123) / 60;
                        Integer sec = CustomBlockEvents.timer.get(world).get(location123) % 60;
                        Integer hour = hold / 60;
                        Integer min = hold % 60;
                        if (hour > 1) {
                            e.getPlayer().sendMessage(ChatColor.YELLOW + "Node will respawn in " + hour + " hours " + min + " minutes and " + sec + " seconds");
                        } else {
                            e.getPlayer().sendMessage(ChatColor.YELLOW + "Node will respawn in " + hour + " hour " + min + " minutes and " + sec + " seconds");
                        }
                    } else if (CustomBlockEvents.timer.get(world).get(location123) >= 60) {
                        Integer min = CustomBlockEvents.timer.get(world).get(location123) / 60;
                        Integer sec = CustomBlockEvents.timer.get(world).get(location123) % 60;
                        if (min > 1) {
                            e.getPlayer().sendMessage(ChatColor.YELLOW + "Node will respawn in " + min + " minutes and " + sec + " seconds");
                        } else {
                            e.getPlayer().sendMessage(ChatColor.YELLOW + "Node will respawn in " + min + " minute and " + sec + " seconds");
                        }
                    } else {
                        if (CustomBlockEvents.timer.get(world).get(location123) > 1) {
                            e.getPlayer().sendMessage(ChatColor.YELLOW + "Node will respawn in " + CustomBlockEvents.timer.get(world).get(location123) + " seconds");
                        } else if (CustomBlockEvents.timer.get(world).get(location123) == 1) {
                            e.getPlayer().sendMessage(ChatColor.YELLOW + "Node will respawn in " + CustomBlockEvents.timer.get(world).get(location123) + " second");
                        } else {
                            e.getPlayer().sendMessage(ChatColor.YELLOW + "Node is ready to be mined!");
                        }
                    }
                }
            }
        }
    }
}