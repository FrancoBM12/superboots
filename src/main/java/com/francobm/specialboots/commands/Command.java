package com.francobm.specialboots.commands;

import com.francobm.specialboots.NBT.NBTTag;
import com.francobm.specialboots.boots.Boots;
import com.francobm.specialboots.utils.UtilsSB;
import com.francobm.specialboots.utils.XMaterial;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            return false;
        }
        Player player = (Player) sender;
        if(args.length >= 1) {
            if(args[0].equalsIgnoreCase("set")){
                // /boots set {type} - specialboots.set.*
                if (args.length >= 2) {
                    ItemStack itemInHand = player.getInventory().getItemInHand();
                    if(itemInHand == null){
                        player.sendMessage(UtilsSB.ChatColor("&cYou need to have an item in hand"));
                        return true;
                    }

                    if(!isBoots(itemInHand)){
                        player.sendMessage(UtilsSB.ChatColor("&cThe item in hand needs to be a boot!"));
                        return true;
                    }
                    try {
                        Boots boots = Boots.valueOf(args[1].toUpperCase());
                        if(player.hasPermission("specialboots.set."+boots.name()) || player.hasPermission("specialboots.*")){
                            NBTTag bCompound = NBTTag.get(itemInHand);
                            switch (boots) {
                                case FLYING_BOOTS:
                                    //net.minecraft.world.item.ItemStack nmsB = CraftItemStack.asNMSCopy(itemInHand);
                                    if(bCompound.getString("superboots-fly").equalsIgnoreCase("1")){
                                        player.sendMessage(UtilsSB.ChatColor("&cThis boot is already special!"));
                                        return true;
                                    }

                                    bCompound.setString("superboots-fly", "1");

                                    player.getInventory().setItemInHand(bCompound.apply(itemInHand));
                                    player.sendMessage(UtilsSB.ChatColor("&asetting the fly to your boots"));
                                    break;
                                case RESISTANCE_BOOTS:
                                    if(bCompound.getString("superboots-resistance").equalsIgnoreCase("1")){
                                        player.sendMessage(UtilsSB.ChatColor("&cThis boot is already special!"));
                                        return true;
                                    }
                                    bCompound.setString("superboots-resistance", "1");
                                    player.getInventory().setItemInHand(bCompound.apply(itemInHand));
                                    player.sendMessage(UtilsSB.ChatColor("&asetting the resistance to your boots"));
                                    break;
                                case ANTI_HUNGER_BOOTS:
                                    if(bCompound.getString("superboots-anti-hunger").equalsIgnoreCase("1")){
                                        player.sendMessage(UtilsSB.ChatColor("&cThis boot is already special!"));
                                        return true;
                                    }
                                    bCompound.setString("superboots-anti-hunger", "1");
                                    player.getInventory().setItemInHand(bCompound.apply(itemInHand));
                                    player.sendMessage(UtilsSB.ChatColor("&asetting the Anti hunger to your boots"));
                                    break;
                                case INVISIBLE_BOOTS:
                                    if(bCompound.getString("superboots-invisible").equalsIgnoreCase("1")){
                                        player.sendMessage(UtilsSB.ChatColor("&cThis boot is already special!"));
                                        return true;
                                    }
                                    bCompound.setString("superboots-invisible", "1");
                                    player.getInventory().setItemInHand(bCompound.apply(itemInHand));
                                    player.sendMessage(UtilsSB.ChatColor("&asetting the Invisible to your boots"));
                                    break;
                                case SPEED_BOOTS:
                                    if(bCompound.getString("superboots-speed").equalsIgnoreCase("1")){
                                        player.sendMessage(UtilsSB.ChatColor("&cThis boot is already special!"));
                                        return true;
                                    }
                                    bCompound.setString("superboots-speed", "1");
                                    player.getInventory().setItemInHand(bCompound.apply(itemInHand));
                                    player.sendMessage(UtilsSB.ChatColor("&asetting the Speed to your boots"));
                                    break;
                                case MINER_BOOTS:
                                    if(bCompound.getString("superboots-miner").equalsIgnoreCase("1")){
                                        player.sendMessage(UtilsSB.ChatColor("&cThis boot is already special!"));
                                        return true;
                                    }
                                    bCompound.setString("superboots-miner", "1");
                                    player.getInventory().setItemInHand(bCompound.apply(itemInHand));
                                    player.sendMessage(UtilsSB.ChatColor("&asetting Miner to your boots"));
                                    break;
                                case JUMP_BOOTS:
                                    if(bCompound.getString("superboots-jump").equalsIgnoreCase("1")){
                                        player.sendMessage(UtilsSB.ChatColor("&cThis boot is already special!"));
                                        return true;
                                    }
                                    bCompound.setString("superboots-jump", "1");
                                    player.getInventory().setItemInHand(bCompound.apply(itemInHand));
                                    player.sendMessage(UtilsSB.ChatColor("&asetting Jump to your boots"));
                                    break;
                                case DEFENCE_BOOTS:
                                    if(bCompound.getString("superboots-defence").equalsIgnoreCase("1")){
                                        player.sendMessage(UtilsSB.ChatColor("&cThis boot is already special!"));
                                        return true;
                                    }
                                    bCompound.setString("superboots-defence", "1");
                                    player.getInventory().setItemInHand(bCompound.apply(itemInHand));
                                    player.sendMessage(UtilsSB.ChatColor("&asetting Defence to your boots"));
                                    break;
                            }
                        }else{
                            player.sendMessage(UtilsSB.ChatColor("You do not have permission to set this type of special boots(specialboots.set."+boots+")"));
                        }
                        return true;
                    } catch (IllegalArgumentException exception) {
                        player.sendMessage(UtilsSB.ChatColor("&cBoots available: &e" + Boots.FLYING_BOOTS.name() + "&c, &e" + Boots.RESISTANCE_BOOTS.name() + "&c, &e" + Boots.ANTI_HUNGER_BOOTS.name() + "&c, &e" + Boots.INVISIBLE_BOOTS.name() + "&c, &e" + Boots.MINER_BOOTS.name() + "&c, &e" + Boots.JUMP_BOOTS.name() + "&c, &e" + Boots.DEFENCE_BOOTS.name()));
                    }
                    return true;
                }
                player.sendMessage(UtilsSB.ChatColor("&cUsage /" + cmd.getName() + " set {type}"));
                return true;
            }else if (args[0].equalsIgnoreCase("give")) {
                // /boots give {type}
                if (args.length >= 2) {
                    try {
                        Boots boots = Boots.valueOf(args[1].toUpperCase());
                        if(player.hasPermission("specialboots.give."+boots.name()) || player.hasPermission("specialboots.*")){
                            ItemStack b;
                            switch (boots) {
                                case FLYING_BOOTS:
                                    b = XMaterial.DIAMOND_BOOTS.parseItem();
                                    NBTTag bCompound = NBTTag.get(b);
                                    bCompound.setString("superboots-fly", "1");
                                    player.getInventory().addItem(bCompound.apply(b));
                                    player.sendMessage(UtilsSB.ChatColor("&aObtaining flying boots"));
                                    break;
                                case RESISTANCE_BOOTS:
                                    b = XMaterial.DIAMOND_BOOTS.parseItem();
                                    bCompound = NBTTag.get(b);
                                    bCompound.setString("superboots-resistance", "1");
                                    player.getInventory().addItem(bCompound.apply(b));
                                    player.sendMessage(UtilsSB.ChatColor("&aObtaining resistance boots"));
                                    break;
                                case ANTI_HUNGER_BOOTS:
                                    b = XMaterial.DIAMOND_BOOTS.parseItem();
                                    bCompound = NBTTag.get(b);
                                    bCompound.setString("superboots-anti-hunger", "1");
                                    player.getInventory().addItem(bCompound.apply(b));
                                    player.sendMessage(UtilsSB.ChatColor("&aObtaining Anti Hunger boots"));
                                    break;
                                case INVISIBLE_BOOTS:
                                    b = XMaterial.DIAMOND_BOOTS.parseItem();
                                    bCompound = NBTTag.get(b);
                                    bCompound.setString("superboots-invisible", "1");
                                    player.getInventory().addItem(bCompound.apply(b));
                                    player.sendMessage(UtilsSB.ChatColor("&aObtaining Invisible boots"));
                                    break;
                                case SPEED_BOOTS:
                                    b = XMaterial.DIAMOND_BOOTS.parseItem();
                                    bCompound = NBTTag.get(b);
                                    bCompound.setString("superboots-speed", "1");
                                    player.getInventory().addItem(bCompound.apply(b));
                                    player.sendMessage(UtilsSB.ChatColor("&aObtaining Invisible boots"));
                                    break;
                                case MINER_BOOTS:
                                    b = XMaterial.DIAMOND_BOOTS.parseItem();
                                    bCompound = NBTTag.get(b);
                                    bCompound.setString("superboots-miner", "1");
                                    player.getInventory().addItem(bCompound.apply(b));
                                    player.sendMessage(UtilsSB.ChatColor("&aObtaining Miner boots"));
                                    break;
                                case JUMP_BOOTS:
                                    b = XMaterial.DIAMOND_BOOTS.parseItem();
                                    bCompound = NBTTag.get(b);
                                    bCompound.setString("superboots-jump", "1");
                                    player.getInventory().addItem(bCompound.apply(b));
                                    player.sendMessage(UtilsSB.ChatColor("&aObtaining Miner boots"));
                                    break;
                                case DEFENCE_BOOTS:
                                    b = XMaterial.DIAMOND_BOOTS.parseItem();
                                    bCompound = NBTTag.get(b);
                                    bCompound.setString("superboots-defence", "1");
                                    player.getInventory().addItem(bCompound.apply(b));
                                    player.sendMessage(UtilsSB.ChatColor("&aObtaining Miner boots"));
                                    break;
                            }
                        }else{
                            player.sendMessage(UtilsSB.ChatColor("You do not have permission to set this type of special boots(specialboots.give."+boots+")"));
                        }
                        return true;
                    } catch (IllegalArgumentException exception) {
                        player.sendMessage(UtilsSB.ChatColor("&cBoots available: &e" + Boots.FLYING_BOOTS.name() + "&c, &e" + Boots.RESISTANCE_BOOTS.name() + "&c, &e" + Boots.ANTI_HUNGER_BOOTS.name() + "&c, &e" + Boots.INVISIBLE_BOOTS.name() + "&c, &e" + Boots.MINER_BOOTS.name() + "&c, &e" + Boots.JUMP_BOOTS.name() + "&c, &e" + Boots.DEFENCE_BOOTS.name()));
                    }
                    return true;
                }
                player.sendMessage(UtilsSB.ChatColor("&cUsage /" + label + " give {type}"));
                return true;
            }
            return true;
        }
        helpCommand(player, label);
        return true;
    }

    public void helpCommand(Player player, String cmd){
        player.sendMessage(UtilsSB.ChatColor("&b/" + cmd + " give {type}"));
        player.sendMessage(UtilsSB.ChatColor("&b/" + cmd + " set {type}"));
    }

    public boolean isBoots(ItemStack itemStack){
        if(itemStack.getType() == XMaterial.LEATHER_BOOTS.parseMaterial()) return true;
        if(itemStack.getType() == XMaterial.CHAINMAIL_BOOTS.parseMaterial()) return true;
        if(itemStack.getType() == XMaterial.GOLDEN_BOOTS.parseMaterial()) return true;
        if(itemStack.getType() == XMaterial.IRON_BOOTS.parseMaterial()) return true;
        if(itemStack.getType() == XMaterial.DIAMOND_BOOTS.parseMaterial()) return true;
        return itemStack.getType() == XMaterial.NETHERITE_BOOTS.parseMaterial();
    }
}
