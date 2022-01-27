package com.francobm.superboots.cache;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerCache {
    private static final Map<String, PlayerCache> players = new HashMap<>();
    private final Player player;
    private boolean boots;
    private boolean defence;
    private boolean flight;
    private boolean saveFlight;


    public PlayerCache(Player player) {
        this.player = player;
        this.flight = false;
        this.defence = false;
        this.boots = false;
        saveFlight = false;
    }

    public static PlayerCache getPlayer(Player player){
        if(players.containsKey(player.getUniqueId().toString())){
            return players.get(player.getUniqueId().toString());
        }
        PlayerCache playerCache = new PlayerCache(player);
        players.put(player.getUniqueId().toString(), playerCache);
        return playerCache;
    }

    public static void removePlayer(Player player){
        players.remove(player.getUniqueId().toString());
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isFlight() {
        if(flight){
            if(player.getAllowFlight()) return flight;
            player.setAllowFlight(flight);
            player.setFlying(flight);
        }
        return flight;
    }

    public void setFlight(boolean flight) {
        if(!flight && isSaveFlight()) {
            setSaveFlight(false);
            this.flight = false;
            return;
        }
        if(flight && player.getAllowFlight()){
            setSaveFlight(true);
            this.flight = true;
            return;
        }
        player.setAllowFlight(flight);
        player.setFlying(flight);
        this.flight = flight;
    }

    public boolean isSaveFlight() {
        return saveFlight;
    }

    public void setSaveFlight(boolean saveFlight) {
        this.saveFlight = saveFlight;
    }

    public boolean isDefence() {
        return defence;
    }

    public void setDefence(boolean defence) {
        this.defence = defence;
    }

    public boolean isBoots() {
        return boots;
    }

    public void setBoots(boolean boots) {
        this.boots = boots;
    }
}
