package com.francobm.superboots.managers;

import com.francobm.superboots.NBT.NBTTag;
import com.francobm.superboots.SuperBoots;
import com.francobm.superboots.cache.PlayerCache;
import com.francobm.superboots.utils.UtilsSB;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class checkBoots {
    private final SuperBoots plugin;

    public checkBoots(SuperBoots plugin){
        this.plugin = plugin;
        check();
    }

    public void check(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.isOnline()) {
                        onEquipBoots(player);
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public void onEquipBoots(Player player){
        PlayerCache playerCache = PlayerCache.getPlayer(player);
        ItemStack boots = player.getInventory().getBoots();
        StringBuilder stringBuilder = new StringBuilder();
        NBTTag bCompound;
        if(boots == null || boots.getType() == Material.AIR){
            if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)){
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                if(plugin.getConfig().getBoolean("messages-enabled")) {
                    stringBuilder.append(plugin.getConfig().getString("messages.names.invisibility")).append(UtilsSB.ChatColor("&7,"));
                }
            }
            if(playerCache.isDefence()){
                playerCache.setDefence(false);
                if(plugin.getConfig().getBoolean("messages-enabled")) {
                    stringBuilder.append(plugin.getConfig().getString("messages.names.defence")).append(UtilsSB.ChatColor("&7,"));
                }
            }
            if(playerCache.isFlight()){
                playerCache.setFlight(false);
                if(plugin.getConfig().getBoolean("messages-enabled")) {
                    stringBuilder.append(plugin.getConfig().getString("messages.names.flight")).append(UtilsSB.ChatColor("&7,"));
                }
            }
            if(player.hasPotionEffect(PotionEffectType.SPEED)){
                player.removePotionEffect(PotionEffectType.SPEED);
                if(plugin.getConfig().getBoolean("messages-enabled")) {
                    stringBuilder.append(plugin.getConfig().getString("messages.names.speed")).append(UtilsSB.ChatColor("&7,"));
                }
            }
            if(player.hasPotionEffect(PotionEffectType.FAST_DIGGING)){
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                if(plugin.getConfig().getBoolean("messages-enabled")) {
                    stringBuilder.append(plugin.getConfig().getString("messages.names.miner")).append(UtilsSB.ChatColor("&7,"));
                }
            }
            if(player.hasPotionEffect(PotionEffectType.JUMP)){
                player.removePotionEffect(PotionEffectType.JUMP);
                if(plugin.getConfig().getBoolean("messages-enabled")) {
                    stringBuilder.append(plugin.getConfig().getString("messages.names.jump")).append(UtilsSB.ChatColor("&7,"));
                }
            }
            if(plugin.getConfig().getBoolean("messages-enabled")) {
                if(!playerCache.isBoots()) return;
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                playerCache.setBoots(false);
                player.sendMessage(plugin.prefix + plugin.getConfig().getString("messages.unequip-boots").replace("%specials%", stringBuilder.toString()));
            }
            return;
        }
        bCompound = NBTTag.get(boots);
        if(bCompound == null) return;
        StringBuilder enable = new StringBuilder();
        if(bCompound.getString("superboots-speed").equalsIgnoreCase("1")){
            if(player.hasPotionEffect(PotionEffectType.SPEED)){
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, false, false));
            if(plugin.getConfig().getBoolean("messages-enabled")) {
                enable.append(plugin.getConfig().getString("messages.names.speed")).append(UtilsSB.ChatColor("&7,"));
            }
        }

        if(bCompound.getString("superboots-defence").equalsIgnoreCase("1")){
            if(playerCache.isDefence()){
                return;
            }
            playerCache.setDefence(true);
            if(plugin.getConfig().getBoolean("messages-enabled")) {
                enable.append(plugin.getConfig().getString("messages.names.defence")).append(UtilsSB.ChatColor("&7,"));
            }
        }

        if(bCompound.getString("superboots-invisible").equalsIgnoreCase("1")){
            if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)){
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 3, false, false));
            if(plugin.getConfig().getBoolean("messages-enabled")) {
                enable.append(plugin.getConfig().getString("messages.names.invisibility")).append(UtilsSB.ChatColor("&7,"));
            }
        }
        if(bCompound.getString("superboots-miner").equalsIgnoreCase("1")){
            if(player.hasPotionEffect(PotionEffectType.FAST_DIGGING)){
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, false, false));
            if(plugin.getConfig().getBoolean("messages-enabled")) {
                enable.append(plugin.getConfig().getString("messages.names.miner")).append(UtilsSB.ChatColor("&7,"));
            }
        }
        if(bCompound.getString("superboots-jump").equalsIgnoreCase("1")){
            if(player.hasPotionEffect(PotionEffectType.JUMP)){
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 4, false, false));
            if(plugin.getConfig().getBoolean("messages-enabled")) {
                enable.append(plugin.getConfig().getString("messages.names.jump")).append(UtilsSB.ChatColor("&7,"));
            }
        }
        if(bCompound.getString("superboots-fly").equalsIgnoreCase("1")){
            if(playerCache.isFlight()){
                return;
            }
            playerCache.setFlight(true);
            if(plugin.getConfig().getBoolean("messages-enabled")) {
                enable.append(plugin.getConfig().getString("messages.names.flight")).append(UtilsSB.ChatColor("&7,"));
            }
        }
        if(plugin.getConfig().getBoolean("messages-enabled")) {
            if(playerCache.isBoots()) return;
            enable.deleteCharAt(enable.length() - 1);
            playerCache.setBoots(true);
            player.sendMessage(plugin.prefix + plugin.getConfig().getString("messages.equip-boots").replace("%specials%", enable.toString()));
        }
    }
}
