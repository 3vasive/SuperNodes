package com.evasive.me.supernodes.commands;

import com.evasive.me.supernodes.SuperNodes;
import com.evasive.me.supernodes.config.NodeConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.logging.Logger;

public class NodeCommands implements CommandExecutor {

    public SuperNodes plugin;

    public NodeCommands(SuperNodes plugin) {
        this.plugin = plugin;
        plugin.getCommand("nodes").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("nodes.admin")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GOLD + "/nodes give {node_name} {playername}");
                sender.sendMessage(ChatColor.GOLD + "/nodes reload");
                return true;
            } else if (args.length == 1) {
                if (args[0].equals("give")) {
                    sender.sendMessage(ChatColor.GOLD + "/nodes give {node_name} {playername}");
                    return true;
                } else if (args[0].equals("reload")) {
                    NodeConfig.reload();
                    sender.sendMessage(ChatColor.YELLOW + "" + SuperNodes.getPlugin(SuperNodes.class).getConfig().getString("reload-message"));
                    return true;
                } else {
                    sender.sendMessage(ChatColor.GOLD + "/nodes give {node_name} {playername}");
                    sender.sendMessage(ChatColor.GOLD + "/nodes reload");
                    return true;
                }
            } else if (args.length == 2) {
                if (args[0].equals("give")) {
                    sender.sendMessage(ChatColor.GOLD + "/nodes give {node_name} {playername}");
                    return true;
                } else if (args[0].equals("reload")) {
                    NodeConfig.reload();
                } else {
                    sender.sendMessage(ChatColor.GOLD + "/nodes give {node_name} {playername}");
                    sender.sendMessage(ChatColor.GOLD + "/nodes reload");
                    return true;
                }
            } else if (args.length == 3) {
                if (args[0].equals("give")) {
                    Player player = Bukkit.getPlayer(args[2]);
                    if (player != null) {
                        NodeConfig.get().getConfigurationSection("Nodes").getKeys(false).forEach(area -> {
                            if (area.equals(args[1])) {
                                String keep = area;
                                Material mat = Material.getMaterial(NodeConfig.get().getString("Nodes." + area + ".BlockMaterial"));
                                ItemStack node = new ItemStack(mat);
                                NodeConfig.get().getConfigurationSection("Nodes." + area).getKeys(false).forEach(iteminfo -> {
                                    if (iteminfo.equals("ItemName")) {
                                        ItemMeta meta = node.getItemMeta();
                                        meta.setDisplayName(NodeConfig.get().getString("Nodes." + area + ".ItemName"));
                                        node.setItemMeta(meta);
                                    }
                                    if (iteminfo.equals("Lore")) {
                                        ItemMeta meta2 = node.getItemMeta();
                                        ArrayList info;
                                        info = (ArrayList) NodeConfig.get().getStringList("Nodes." + area + ".Lore");
                                        meta2.setLore(info);
                                        node.setItemMeta(meta2);
                                    }
                                    if(iteminfo.equals("Glowing")){
                                        if (NodeConfig.get().getString("Nodes." + area + ".Glowing").equals("True")){
                                            ItemMeta meta3 = node.getItemMeta();
                                            meta3.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                                            meta3.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                            node.setItemMeta(meta3);
                                        }
                                    }
                                    if (iteminfo.equals("Drop")) {
                                        NamespacedKey nbt = new NamespacedKey(plugin, "Node");
                                        ItemMeta nbttag = node.getItemMeta();
                                        nbttag.getPersistentDataContainer().set(nbt, PersistentDataType.STRING, area);
                                        node.setItemMeta(nbttag);
                                        player.getInventory().addItem(node);
                                    }
                                });
                                player.sendMessage(ChatColor.AQUA + "You have been given a " + keep + " node");
                            }
                        });
                    }
                    return true;
                } else {
                    sender.sendMessage(ChatColor.GOLD + "/nodes reload");
                }
            } else {
                sender.sendMessage(ChatColor.GOLD + "/nodes give {node_name} {playername}");
                sender.sendMessage(ChatColor.GOLD + "/nodes reload");
                return true;
            }
            sender.sendMessage(ChatColor.GOLD + "/nodes give {node_name} {playername}");
            sender.sendMessage(ChatColor.GOLD + "/nodes reload");
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        }
        return true;
    }

}
