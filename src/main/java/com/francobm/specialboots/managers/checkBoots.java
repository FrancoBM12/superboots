package com.francobm.specialboots.managers;

import com.francobm.specialboots.NBT.NBTTag;
import com.francobm.specialboots.SpecialBoots;
import com.francobm.specialboots.cache.PlayerCache;
import com.francobm.specialboots.utils.UtilsSB;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

public class checkBoots {
    private final SpecialBoots plugin;

    public checkBoots(SpecialBoots plugin){
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
        NBTTag bCompound = null;
        if(boots != null){
            bCompound = NBTTag.get(boots);
        }

        if(boots == null || boots.getType() == Material.AIR){
            if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)){
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                player.sendMessage(UtilsSB.ChatColor("&cInvisibility desactivated"));
            }
            if(playerCache.isFlight()){
                playerCache.setFlight(false);
                player.sendMessage(UtilsSB.ChatColor("&cFlight desactived"));
            }
            if(player.hasPotionEffect(PotionEffectType.SPEED)){
                player.removePotionEffect(PotionEffectType.SPEED);
                player.sendMessage(UtilsSB.ChatColor("&cSpeed desactivated"));
            }
            if(player.hasPotionEffect(PotionEffectType.FAST_DIGGING)){
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                player.sendMessage(UtilsSB.ChatColor("&cMiner mode desactivated"));
            }
            if(player.hasPotionEffect(PotionEffectType.JUMP)){
                player.removePotionEffect(PotionEffectType.JUMP);
                player.sendMessage(UtilsSB.ChatColor("&cJump desactivated"));
            }
            return;
        }

        if(bCompound != null && bCompound.getString("superboots-speed").equalsIgnoreCase("1")){
            if(player.hasPotionEffect(PotionEffectType.SPEED)){
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, false, false));
            player.sendMessage(UtilsSB.ChatColor("&aSpeed activated"));
        }

        if(bCompound != null && bCompound.getString("superboots-invisible").equalsIgnoreCase("1")){
            if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)){
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 3, false, false));
            player.sendMessage(UtilsSB.ChatColor("&aInvisibility activated"));
        }
        if(bCompound != null && bCompound.getString("superboots-miner").equalsIgnoreCase("1")){
            if(player.hasPotionEffect(PotionEffectType.FAST_DIGGING)){
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, false, false));
            player.sendMessage(UtilsSB.ChatColor("&aMiner mode activated"));
        }
        if(bCompound != null && bCompound.getString("superboots-jump").equalsIgnoreCase("1")){
            if(player.hasPotionEffect(PotionEffectType.JUMP)){
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 4, false, false));
            player.sendMessage(UtilsSB.ChatColor("&aJump activated"));
        }
        if(bCompound != null && bCompound.getString("superboots-fly").equalsIgnoreCase("1")){
            if(playerCache.isFlight()){
                return;
            }
            playerCache.setFlight(true);
            player.sendMessage(UtilsSB.ChatColor("&aFlight actived"));
        }
    }
}
