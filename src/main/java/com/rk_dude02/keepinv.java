package com.rk_dude02;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;


public final class keepinv extends JavaPlugin implements Listener {
    Integer pluginID = 16183;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Welcome to VoidKeepInv, created by RK_Dude02");
        getLogger().info("VoidKeepInv is starting...");
        getLogger().info("Registering Listeners and enabling bStats Metrics");
        getServer().getPluginManager().registerEvents(this, this);
        Metrics metrics = new Metrics(this, pluginID);
        getLogger().info("VoidkeepInv is ready! Enjoy!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutting Down VoidKeepInv");
        // Plugin shutdown logic
    }
    @EventHandler
    public void onPlayerVoidDeath(PlayerDeathEvent e) {
        e.getEntity();
        Player p = e.getEntity();
        if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID) {
            String pName = p.getDisplayName();
            getLogger().info(pName + " died from the void. They will receive their items back when they respawn.");
            e.setKeepLevel(true);
            e.setDroppedExp(0);
            e.getDrops().clear();
            e.setKeepInventory(true);
        }
    }
}
