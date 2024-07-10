package me.h0spq;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public final class SpawnerTransfer extends JavaPlugin {
    public static ItemStack pickaxe;

    @Override
    public void onEnable() {
        setDefaultPickaxe();
        getLogger().log(Level.INFO, color("&7[&6SpawnerTransfer&7] loading pickaxe"));
        onLoad();
        if (getCommand("spawnertransfer") != null) {
            getLogger().log(Level.INFO, color("&7[&6SpawnerTransfer&7] registering command"));
            getCommand("spawnertransfer").setExecutor(this);
            getCommand("spawnertransfer").setTabCompleter(this);

        }
        getLogger().log(Level.INFO, color("&7[&6SpawnerTransfer&7] registering listeners"));
        Bukkit.getServer().getPluginManager().registerEvents(new BreakListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlaceListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PickaxeGui(), this);
    }
    @Override
    public void onDisable() {
        getLogger().log(Level.OFF, color("&7[&6SpawnerTransfer&7] bye-bye babe "));
        getConfig().set("spawnertransfer.pickaxe", pickaxe);
        saveConfig();

    }
    public void onLoad() {
        if (getConfig().get("spawnertransfer.pickaxe") != null) {
            ItemStack configPickaxe = getConfig().getItemStack("spawnertransfer.pickaxe");
            if (configPickaxe != null) {
                pickaxe = configPickaxe;
            } else {
                getLogger().log(Level.CONFIG, color("&7[&6SpawnerTransfer&7] Your pickaxe is not an ItemStack!"));
                setDefaultPickaxe();
                saveConfig();
            }
        } else {
            getLogger().log(Level.CONFIG, color("&7[&6SpawnerTransfer&7] Your pickaxe is null"));
            setDefaultPickaxe();
            saveConfig();
        }
    }
    private void setDefaultPickaxe() {
        pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = pickaxe.getItemMeta();
        meta.setDisplayName(color("&bSpawner Pickaxe"));
        List<String> lore = new ArrayList<>();
        lore.add(color("&7This pickaxe can break spawners"));
        meta.setLore(lore);
        pickaxe.setItemMeta(meta);
    }
    static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("spawnertransfer") && sender.hasPermission("spawnertransfer.give")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("change")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    PickaxeGui.openInventory(player);
                    return true;
                } else {
                    sender.sendMessage(color("&7[&6SpawnerTransfer&7] This command can only be run by a player."));
                    return true;
                }
            } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
                Player player = Bukkit.getPlayer(args[1]);
                if (player != null) {
                    try {
                        int amount = Integer.parseInt(args[2]);
                        ItemStack pickaxeClone = pickaxe.clone();
                        pickaxeClone.setAmount(amount);
                        player.getInventory().addItem(pickaxeClone);
                        sender.sendMessage(color("&7[&6SpawnerTransfer&7] Given pickaxe to player " + player.getName() + " with amount " + amount));
                    } catch (NumberFormatException e) {
                        sender.sendMessage(color("&7[&6SpawnerTransfer&7] Invalid amount: " + args[2]));
                    }
                } else {
                    sender.sendMessage(color("&7[&6SpawnerTransfer&7] The player " + args[1] + " is not on the server"));
                }
            } else {
                sender.sendMessage(color("&7[&6SpawnerTransfer&7] Usage: /spawnertransfer give <player> <amount>"));
                sender.sendMessage(color("&7[&6SpawnerTransfer&7] Usage: /spawnertransfer change"));
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("spawnertransfer") && sender.hasPermission("spawnertransfer.give")) {
            List<String> suggestions = new ArrayList<>();
            if (args.length == 1) {
                suggestions.add("change");
                suggestions.add("give");
            } else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
                Bukkit.getOnlinePlayers().forEach(player -> suggestions.add(player.getName()));
            } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
                for (int i = 1; i <= 64; i++) {
                    suggestions.add(String.valueOf(i));
                }
            }
            return suggestions;
        }
        return Collections.emptyList();
    }



}
