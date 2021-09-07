package com.francobm.specialboots.managers;

import com.francobm.specialboots.NBT.NBTTag;
import com.francobm.specialboots.SpecialBoots;
import com.francobm.specialboots.utils.UtilsSB;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class checkBoots {
    private final SpecialBoots plugin;

    public checkBoots(SpecialBoots plugin){
        this.plugin = plugin;
        check();
    }

    public void check(){
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.isOnline()) {
                        onEquipBoots(player);
                    }
                }
            }
        }, 0L, 20L);
    }

    public void onEquipBoots(Player player){
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
            if(player.getAllowFlight()){
                player.setAllowFlight(false);
                player.setFlying(false);
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
        }

        if(bCompound != null && bCompound.getString("superboots-speed").equalsIgnoreCase("1")){
            if(player.hasPotionEffect(PotionEffectType.SPEED)){
                return;
            }
            player.addPotionEffect(PotionEffectType.SPEED.createEffect(3600*20, 3));
            player.sendMessage(UtilsSB.ChatColor("&aSpeed activated"));
        }

        if(bCompound != null && bCompound.getString("superboots-invisible").equalsIgnoreCase("1")){
            if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)){
                return;
            }
            player.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(3600*20, 3));
            player.sendMessage(UtilsSB.ChatColor("&aInvisibility activated"));
        }
        if(bCompound != null && bCompound.getString("superboots-miner").equalsIgnoreCase("1")){
            if(player.hasPotionEffect(PotionEffectType.FAST_DIGGING)){
                return;
            }
            player.addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(3600*20, 1));
            player.sendMessage(UtilsSB.ChatColor("&aMiner mode activated"));
        }
        if(bCompound != null && bCompound.getString("superboots-jump").equalsIgnoreCase("1")){
            if(player.hasPotionEffect(PotionEffectType.JUMP)){
                return;
            }
            player.addPotionEffect(PotionEffectType.JUMP.createEffect(3600*20, 4));
            player.sendMessage(UtilsSB.ChatColor("&aJump activated"));
        }
        if(bCompound != null && bCompound.getString("superboots-fly").equalsIgnoreCase("1")){
            if(player.getAllowFlight()){
                return;
            }
            player.setAllowFlight(true);
            player.setFlying(true);
            player.sendMessage(UtilsSB.ChatColor("&aFlight actived"));
        }

        if(bCompound != null && bCompound.getString("superboots-anti-hunger").equalsIgnoreCase("1")) {
            if(player.getFoodLevel() < 20){
                player.setFoodLevel(player.getFoodLevel()+1);
            }
        }
    }
}
