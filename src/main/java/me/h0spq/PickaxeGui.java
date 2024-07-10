package me.h0spq;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;

public class PickaxeGui implements Listener {

    public static Inventory inventory = Bukkit.createInventory(null, 9, SpawnerTransfer.color("&3Set your pickaxe there"));

    @EventHandler
    public void onGuiClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals(SpawnerTransfer.color("&3Set your pickaxe there"))) {
            ItemStack item = event.getInventory().getItem(4);
            if (item != null && item.getType() != Material.AIR) {
                SpawnerTransfer.pickaxe = item;

            }
        }
    }

    public static void openInventory(Player player) {
        ItemStack glass1 = createGlassPane(SpawnerTransfer.color("&2⏹"));
        ItemStack glass2 = createGlassPane(SpawnerTransfer.color("&2>"));
        ItemStack glass3 = createGlassPane(SpawnerTransfer.color("&2<"));

        for (int i = 0; i < 4; i++) {
            inventory.setItem(i, glass1);
        }
        for (int i = 5; i < 9; i++) {
            inventory.setItem(i, glass1);
        }
        inventory.setItem(3, glass2);
        inventory.setItem(5,glass3);

        inventory.setItem(4, SpawnerTransfer.pickaxe);
        player.openInventory(inventory);
    }

    private static ItemStack createGlassPane(String displayName) {
        ItemStack glass = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta meta = glass.getItemMeta();
        meta.setDisplayName(displayName);
        glass.setItemMeta(meta);
        return glass;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(SpawnerTransfer.color("&3Set your pickaxe there"))) {
            if (event.getRawSlot() < 9 && event.getSlot() != 4) {
                event.setCancelled(true);
            }
        }
    }


}
