package me.h0spq;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class BreakListener implements Listener {

    @EventHandler
    public void onBlockPlace(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (action == Action.LEFT_CLICK_BLOCK && player.getInventory().getItemInMainHand().isSimilar(SpawnerTransfer.pickaxe)) {
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.SPAWNER) {
                CreatureSpawner spawner = (CreatureSpawner) event.getClickedBlock().getState();
                EntityType spawnerType = spawner.getSpawnedType();
                ItemStack spawnerItem = new ItemStack(Material.SPAWNER);
                BlockStateMeta spawnerMeta = (BlockStateMeta) spawnerItem.getItemMeta();
                CreatureSpawner spawnerBlockState = (CreatureSpawner) spawnerMeta.getBlockState();
                spawnerBlockState.setSpawnedType(spawnerType);
                spawnerMeta.setBlockState(spawnerBlockState);
                spawnerItem.setItemMeta(spawnerMeta);
                player.getInventory().addItem(spawnerItem);
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                event.getClickedBlock().setType(Material.AIR);
            }
        }
    }


}
