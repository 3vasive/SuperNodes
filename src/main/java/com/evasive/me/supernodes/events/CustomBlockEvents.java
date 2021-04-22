package com.evasive.me.supernodes.events;

import com.evasive.me.supernodes.SuperNodes;
import com.evasive.me.supernodes.config.NodeConfig;
import com.evasive.me.supernodes.manager.CustomBlockManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class CustomBlockEvents implements Listener {

    public CustomBlockEvents(SuperNodes plugin) {
        this.plugin = plugin;
    }

    public static CustomBlockManager customBlockManager = new CustomBlockManager();
    public SuperNodes plugin;
    int Timer = 0;
    public static HashMap<String, Integer> table = new HashMap<String, Integer>();
    public static Map<String, Map<String, Integer>> timer = new HashMap<String, Map<String, Integer>>();
    int taskID = 0;

    @EventHandler
    public void mineNode(BlockBreakEvent e) {
        String world = String.valueOf(e.getBlock().getLocation().getWorld());
        Player player = e.getPlayer();
        Location location = e.getBlock().getLocation();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        String location123 = ("" + x + "," + y + "," + z);
        if (customBlockManager.isCustomBlock(String.valueOf(e.getBlock().getLocation().getWorld()), location123)) {
            NodeConfig.get().getConfigurationSection("Nodes").getKeys(false).forEach(area -> {
                if (area.equals(customBlockManager.getCustomBlockName(String.valueOf(e.getBlock().getLocation().getWorld()), location123))) {
                    Material mat = Material.getMaterial(NodeConfig.get().getString("Nodes." + area + ".Drop"));
                    Material ori = Material.getMaterial(NodeConfig.get().getString("Nodes." + area + ".BlockMaterial"));
                    Material sec = Material.getMaterial(NodeConfig.get().getString("Nodes." + area + ".SecondaryMaterial"));
                    if (ori == e.getBlock().getType()) {
                        int Timer = NodeConfig.get().getInt("Nodes." + area + ".RespawnTime");
                        e.setCancelled(true);
                        if (!player.isSneaking()) {
                            Location above = e.getBlock().getLocation();
                            above.setY(above.getBlockY() + 0.5);
                            e.getBlock().getLocation().getWorld().dropItemNaturally(above, new ItemStack(mat));
                        }
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {
                                e.getBlock().setType(sec);
                            }
                        }, 2);
                        if (!timer.containsKey(world)) {
                            timer.put((world), new HashMap<>());
                        }
                        timer.get(world).put(location123, Timer);
                        taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SuperNodes.getPlugin(SuperNodes.class), new TimerTask() {
                            @Override
                            public void run() {
                                if (timer.get(world).get(location123) == 0) {
                                    Bukkit.getScheduler().cancelTask(table.get(location123));
                                    e.getBlock().setType(ori);
                                } else {
                                    String world = String.valueOf(e.getBlock().getLocation().getWorld());
                                    timer.get(world).put(location123, timer.get(world).get(location123) - 1);
                                }
                            }
                        }, 20, 20);
                        table.put(location123, taskID);
                        if (e.getPlayer().isSneaking()) {
                            e.setCancelled(true);
                            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                public void run() {
                                    e.getBlock().setType(Material.AIR);
                                }
                            }, 2);
                            Material mater = Material.getMaterial(NodeConfig.get().getString("Nodes." + area + ".BlockMaterial"));
                            ItemStack node = new ItemStack(mater);
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
                                        node.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
                                        ItemMeta meta3 = node.getItemMeta();
                                        meta3.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                        node.setItemMeta(meta3);
                                    }
                                }
                                if (iteminfo.equals("Drop")) {
                                    NamespacedKey nbt = new NamespacedKey(plugin, "Node");
                                    ItemMeta nbttag = node.getItemMeta();
                                    nbttag.getPersistentDataContainer().set(nbt, PersistentDataType.STRING, area);
                                    node.setItemMeta(nbttag);
                                    e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(node));
                                }
                            });
                            //
                            for (Map.Entry<String, Integer> entry : table.entrySet()) {
                                if (entry.getKey().equals(location123)) {
                                    Bukkit.getScheduler().cancelTask(entry.getValue());
                                }
                            }
                            customBlockManager.removeCustomBlock(String.valueOf(e.getBlock().getLocation().getWorld()), location123);
                            timer.get(world).remove(location123);
                            e.getPlayer().sendMessage("You have picked up a " + area + " node");
                        }
                    } else {
                        if (e.getPlayer().isSneaking()) {
                            e.setCancelled(true);
                            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                public void run() {
                                    e.getBlock().setType(Material.AIR);
                                }
                            }, 2);
                            //
                            Material mater = Material.getMaterial(NodeConfig.get().getString("Nodes." + area + ".BlockMaterial"));
                            ItemStack node = new ItemStack(mater);
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
                                    e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(node));
                                    e.getPlayer().sendMessage("You have picked up a " + area + " node");
                                }
                            });
                            //
                            for (Map.Entry<String, Integer> entry : table.entrySet()) {
                                if (entry.getKey().equals(location123)) {
                                    Bukkit.getScheduler().cancelTask(entry.getValue());
                                }
                            }
                            customBlockManager.removeCustomBlock(String.valueOf(e.getBlock().getLocation().getWorld()), location123);
                            timer.get(world).remove(location123);
                        }
                        e.setCancelled(true);
                    }
                }
            });
        }
    }

    @EventHandler
    public void placeNode(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        String world = String.valueOf(e.getBlock().getLocation().getWorld());
        NodeConfig.get().getConfigurationSection("Nodes").getKeys(false).forEach(area -> {
            ItemStack itemStack = e.getItemInHand();
            NamespacedKey nbt = new NamespacedKey(plugin, "Node");
            ItemMeta itemMeta = itemStack.getItemMeta();
            PersistentDataContainer container = itemMeta.getPersistentDataContainer();
            if (container.has(nbt, PersistentDataType.STRING)) {
                String foundValue = container.get(nbt, PersistentDataType.STRING);
                if (foundValue.equals(area)) {
                    Location location = e.getBlock().getLocation();
                    int x = location.getBlockX();
                    int y = location.getBlockY();
                    int z = location.getBlockZ();
                    String location123 = ("" + x + "," + y + "," + z);
                    if (!timer.containsKey(world)) {
                        timer.put((world), new HashMap<>());
                    }
                    customBlockManager.initWorld(String.valueOf(e.getBlock().getLocation().getWorld()));
                    customBlockManager.addCustomBlock(String.valueOf(e.getBlock().getLocation().getWorld()), area, location123);
                    //add to list of blocks here
                    Material ori = Material.getMaterial(NodeConfig.get().getString("Nodes." + area + ".BlockMaterial"));
                    Material sec = Material.getMaterial(NodeConfig.get().getString("Nodes." + area + ".SecondaryMaterial"));
                    Timer = NodeConfig.get().getInt("Nodes." + area + ".RespawnTime");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            e.getBlock().setType(sec);
                        }
                    }, 1);
                    timer.get(world).put(location123, Timer);
                    taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SuperNodes.getPlugin(SuperNodes.class), new TimerTask() {
                        @Override
                        public void run() {
                            if (timer.get(world).get(location123) == 0) {
                                Bukkit.getScheduler().cancelTask(table.get(location123));
                                e.getBlock().setType(ori);
                            } else {
                                timer.get(world).put(location123, timer.get(world).get(location123) - 1);
                            }
                        }
                    }, 20, 20);
                    table.put(location123, taskID);
                    player.sendMessage(ChatColor.AQUA + "" + foundValue + " Node Placed");
                }
            }
        });
    }

    @EventHandler
    public void onExplode(final EntityExplodeEvent event) {
        final List<Block> affectedBlocks = event.blockList();
        for (final Block block : affectedBlocks) {
            Location location = block.getLocation();
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();
            String location123 = ("" + x + "," + y + "," + z);
            if (customBlockManager.isCustomBlock(String.valueOf(block.getWorld()), location123)) {
                event.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void onPistonEvent(BlockPistonExtendEvent e) {
        final List<Block> affectedBlocks = e.getBlocks();
        for (final Block block : affectedBlocks) {
            Location location = block.getLocation();
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();
            String location123 = ("" + x + "," + y + "," + z);
            if (customBlockManager.isCustomBlock(String.valueOf(block.getWorld()), location123)) {
                e.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void onPistonEvent(BlockPistonRetractEvent e) {
        final List<Block> affectedBlocks = e.getBlocks();
        for (final Block block : affectedBlocks) {
            Location location = block.getLocation();
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();
            String location123 = ("" + x + "," + y + "," + z);
            if (customBlockManager.isCustomBlock(String.valueOf(block.getWorld()), location123)) {
                e.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void entityEvent(EntityChangeBlockEvent e) {
        Location location = e.getBlock().getLocation();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        String location123 = ("" + x + "," + y + "," + z);
        if (customBlockManager.isCustomBlock(String.valueOf(e.getBlock().getWorld()), location123)) {
            e.setCancelled(true);
        }
    }
}
