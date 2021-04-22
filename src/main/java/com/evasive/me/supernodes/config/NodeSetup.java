package com.evasive.me.supernodes.config;

import com.evasive.me.supernodes.SuperNodes;

import java.util.ArrayList;
import java.util.List;

public class NodeSetup {
    public SuperNodes plugin;

    public NodeSetup(SuperNodes plugin) {
        this.plugin = plugin;
    }

    public void NodeConfigSetup() {
        //Coal Node
        List coal_list = new ArrayList();
        coal_list.add("§b§lTimer: §r§715 Seconds");
        coal_list.add("§b§lLoot: §r§71 Coal");
        NodeConfig.setup();
        NodeConfig.get().addDefault("Nodes.Coal.BlockMaterial", "COAL_ORE");
        NodeConfig.get().addDefault("Nodes.Coal.SecondaryMaterial", "STONE");
        NodeConfig.get().addDefault("Nodes.Coal.ItemName", "§8§lCoal Node");
        NodeConfig.get().addDefault("Nodes.Coal.Lore", coal_list);
        NodeConfig.get().addDefault("Nodes.Coal.RespawnTime", 15);
        NodeConfig.get().addDefault("Nodes.Coal.Glowing", "True");
        NodeConfig.get().addDefault("Nodes.Coal.Drop", "COAL");
        NodeConfig.get().options().copyDefaults(true);
        NodeConfig.save();
        //

        //Iron Node
        List iron_list = new ArrayList();
        iron_list.add("§b§lTimer: §r§730 Seconds");
        iron_list.add("§b§lLoot: §r§71 Iron Ingot");
        NodeConfig.setup();
        NodeConfig.get().addDefault("Nodes.Iron.BlockMaterial", "IRON_ORE");
        NodeConfig.get().addDefault("Nodes.Iron.SecondaryMaterial", "STONE");
        NodeConfig.get().addDefault("Nodes.Iron.ItemName", "§6§lIron Node");
        NodeConfig.get().addDefault("Nodes.Iron.Lore", iron_list);
        NodeConfig.get().addDefault("Nodes.Iron.RespawnTime", 30);
        NodeConfig.get().addDefault("Nodes.Iron.Glowing", "True");
        NodeConfig.get().addDefault("Nodes.Iron.Drop", "IRON_INGOT");
        NodeConfig.get().options().copyDefaults(true);
        NodeConfig.save();
        //

        //Gold Node
        List gold_list = new ArrayList();
        gold_list.add("§b§lTimer: §r§71 Minute");
        gold_list.add("§b§lLoot: §r§71 Gold Ingot");
        NodeConfig.setup();
        NodeConfig.get().addDefault("Nodes.Gold.BlockMaterial", "GOLD_ORE");
        NodeConfig.get().addDefault("Nodes.Gold.SecondaryMaterial", "STONE");
        NodeConfig.get().addDefault("Nodes.Gold.ItemName", "§e§lGold Node");
        NodeConfig.get().addDefault("Nodes.Gold.Lore", gold_list);
        NodeConfig.get().addDefault("Nodes.Gold.RespawnTime", 60);
        NodeConfig.get().addDefault("Nodes.Gold.Glowing", "True");
        NodeConfig.get().addDefault("Nodes.Gold.Drop", "GOLD_INGOT");
        NodeConfig.get().options().copyDefaults(true);
        NodeConfig.save();
        //

        //Diamond Node
        List diamond_list = new ArrayList();
        diamond_list.add("§b§lTimer: §r§72 Minutes");
        diamond_list.add("§b§lLoot: §r§71 Diamond");
        NodeConfig.setup();
        NodeConfig.get().addDefault("Nodes.Diamond.BlockMaterial", "DIAMOND_ORE");
        NodeConfig.get().addDefault("Nodes.Diamond.SecondaryMaterial", "STONE");
        NodeConfig.get().addDefault("Nodes.Diamond.ItemName", "§b§lDiamond Node");
        NodeConfig.get().addDefault("Nodes.Diamond.Lore", diamond_list);
        NodeConfig.get().addDefault("Nodes.Diamond.RespawnTime", 120);
        NodeConfig.get().addDefault("Nodes.Diamond.Glowing", "True");
        NodeConfig.get().addDefault("Nodes.Diamond.Drop", "DIAMOND");
        NodeConfig.get().options().copyDefaults(true);
        NodeConfig.save();
        //

        //Diamond Node
        List emerald_list = new ArrayList();
        emerald_list.add("§b§lTimer: §r§73 Minutes");
        emerald_list.add("§b§lLoot: §r§71 Emerald");
        NodeConfig.setup();
        NodeConfig.get().addDefault("Nodes.Emerald.BlockMaterial", "EMERALD_ORE");
        NodeConfig.get().addDefault("Nodes.Emerald.SecondaryMaterial", "STONE");
        NodeConfig.get().addDefault("Nodes.Emerald.ItemName", "§a§lEmerald Node");
        NodeConfig.get().addDefault("Nodes.Emerald.Lore", emerald_list);
        NodeConfig.get().addDefault("Nodes.Emerald.RespawnTime", 180);
        NodeConfig.get().addDefault("Nodes.Emerald.Glowing", "True");
        NodeConfig.get().addDefault("Nodes.Emerald.Drop", "EMERALD");
        NodeConfig.get().options().copyDefaults(true);
        NodeConfig.save();
        //

        //Example at top
        List example_list = new ArrayList();
        example_list.add("#Use '-' for new lore lines (use § for color codes instead of &)");
        example_list.add("#Use '-' for new lore lines (use § for color codes instead of &)");
        NodeConfig.setup();
        NodeConfig.get().addDefault("#Example.#BlockMaterial", "Block ID");
        NodeConfig.get().addDefault("#Example.#SecondaryMaterial", "Block ID");
        NodeConfig.get().addDefault("#Example.#ItemName", "Name of node (use § for color codes instead of &)");
        NodeConfig.get().addDefault("#Example.#Lore", example_list);
        NodeConfig.get().addDefault("#Example.#RespawnTime", "Number of seconds you want node to take to respawn");
        NodeConfig.get().addDefault("#Example.#Glowing", "True");
        NodeConfig.get().addDefault("#Example.#Drop", "Item/Block ID");
        NodeConfig.get().options().copyDefaults(true);
        NodeConfig.save();
        //
    }
}