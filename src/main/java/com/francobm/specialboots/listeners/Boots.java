package com.francobm.specialboots.listeners;

import com.francobm.specialboots.NBT.NBTTag;
import com.francobm.specialboots.utils.UtilsSB;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class Boots implements Listener {

    @EventHandler
    public void onMinerBoots(BlockBreakEvent event){
        Player player = event.getPlayer();
        ItemStack boots = player.getInventory().getBoots();
        if(boots == null) return;
        NBTTag bCompound = NBTTag.get(boots);
        if(bCompound != null && bCompound.getString("superboots-miner").equalsIgnoreCase("1")) {
            for(ItemStack itemStack : event.getBlock().getDrops()){
                if(itemStack == null) return;
                player.getWorld().dropItemNaturally(event.getBlock().getLocation(), itemStack);
            }
            event.setExpToDrop(event.getExpToDrop()*2);
        }
    }

    @EventHandler
    public void onDefenceBoots(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            ItemStack boots = player.getInventory().getBoots();
            if(boots == null) return;
            NBTTag bCompound = NBTTag.get(boots);
            if(bCompound != null && bCompound.getString("superboots-defence").equalsIgnoreCase("1")) {
                double percentage = UtilsSB.percent(50, event.getDamage());
                player.sendMessage(UtilsSB.ChatColor("&c-50% Damage: &b" + percentage));
                event.setDamage(percentage);
            }
            if(event.getDamager() instanceof Player){
                Player damager = (Player) event.getDamager();
                if(bCompound != null && bCompound.getString("superboots-defence").equalsIgnoreCase("1")) {
                    double percentage = UtilsSB.percent(10, event.getDamage());
                    player.sendMessage(UtilsSB.ChatColor("&a+10% Damage to attacker: &b" + percentage));
                    damager.damage(percentage);
                }
            }
        }
    }
    @EventHandler
    public void onDamageBoots(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            ItemStack boots = player.getInventory().getBoots();
            if(boots == null) return;
            NBTTag bCompound = NBTTag.get(boots);
            if(bCompound != null && bCompound.getString("superboots-resistance").equalsIgnoreCase("1")) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.LAVA || event.getCause() == EntityDamageEvent.DamageCause.FALL || event.getCause() == EntityDamageEvent.DamageCause.POISON) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
