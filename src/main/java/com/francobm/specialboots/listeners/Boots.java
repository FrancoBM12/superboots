package com.francobm.specialboots.listeners;

import com.francobm.specialboots.NBT.NBTTag;
import com.francobm.specialboots.cache.PlayerCache;
import com.francobm.specialboots.utils.UtilsSB;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

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
    public void onHunger(FoodLevelChangeEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        ItemStack boots = player.getInventory().getBoots();
        if(boots == null) return;
        NBTTag bCompound = NBTTag.get(boots);
        if(bCompound != null && bCompound.getString("superboots-anti-hunger").equalsIgnoreCase("1")) {
            if(event.getFoodLevel() != 20) {
                event.setFoodLevel(20);
                return;
            }
            player.setSaturation(10f);
            player.setExhaustion(0f);
            event.setCancelled(true);
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
                player.sendMessage(UtilsSB.ChatColor("&9Defence: &c-50% Damage: &e" + percentage));
                event.setDamage(percentage);
            }
            if(event.getDamager() instanceof LivingEntity){
                LivingEntity attacker = (LivingEntity) event.getDamager();
                if(bCompound != null && bCompound.getString("superboots-defence").equalsIgnoreCase("1")) {
                    double percentage = UtilsSB.percent(10, event.getDamage());
                    player.sendMessage(UtilsSB.ChatColor("&9Defence: &a+10% Damage to attacker: &e" + percentage));
                    attacker.damage(percentage);
                }
                return;
            }
            if(event.getDamager() instanceof Projectile){
                Projectile projectile = (Projectile) event.getDamager();
                LivingEntity attacker = (LivingEntity) projectile.getShooter();
                if(attacker == null) return;
                if(player.getName().equalsIgnoreCase(attacker.getName())) return;
                if(bCompound != null && bCompound.getString("superboots-defence").equalsIgnoreCase("1")) {
                    double percentage = UtilsSB.percent(10, event.getDamage());
                    player.sendMessage(UtilsSB.ChatColor("&9Defence: &a+10% Damage to attacker: &e" + percentage));
                    attacker.damage(percentage);
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

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        PlayerCache.removePlayer(event.getPlayer());
    }
}
