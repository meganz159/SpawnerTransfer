package me.h0spq;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class PlaceListener implements Listener {
    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItemInHand();
        if(itemInHand.getType().equals(Material.SPAWNER)) {
            Block placedBlock = event.getBlockPlaced();
            CreatureSpawner creatureSpawner = (CreatureSpawner) placedBlock.getState();
            BlockStateMeta spawnerMeta = (BlockStateMeta)  itemInHand.getItemMeta();
            CreatureSpawner spawnerBlockState = (CreatureSpawner) spawnerMeta.getBlockState();
            creatureSpawner.setSpawnedType(spawnerBlockState.getSpawnedType());
            creatureSpawner.update();
            if (itemInHand.getAmount() > 1) {
                itemInHand.setAmount(itemInHand.getAmount() - 1);
            } else {
                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            }
        }
    }
}
