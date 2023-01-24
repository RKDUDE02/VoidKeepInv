package com.rk_dude02;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
        this.saveDefaultConfig();
        getLogger().info("Welcome to VoidKeepInv 1.1, created by RK_Dude02");
        getLogger().info("VoidKeepInv is starting...");
        getLogger().info("Registering Listeners and enabling bStats Metrics");
        getServer().getPluginManager().registerEvents(this, this);
        Metrics metrics = new Metrics(this, pluginID);
        getLogger().info("Registering Reload Config Command");
        this.getCommand("vkireload").setExecutor(new CommandReloadConfig());
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
            Boolean logConsoleVoidDeath = this.getConfig().getBoolean("logVoidDeathsToConsole");
            if (logConsoleVoidDeath) {
                getLogger().info(pName + " died from the void. They will receive their items back when they respawn.");
            }
            Boolean restorePlayerXP = this.getConfig().getBoolean("restorePlayerXP");
            if (restorePlayerXP) {
                e.setKeepLevel(true);
            }
            e.setDroppedExp(0);
            e.getDrops().clear();
            e.setKeepInventory(true);
            Boolean tellPlayerItemsRestored = this.getConfig().getBoolean("tellPlayerItemsRestored");
            if (tellPlayerItemsRestored) {
                p.sendMessage(ChatColor.GREEN + "Your inventory will be restored when you respawn because you died to the void!");
            }
            Boolean tellPlayerNotRestored = this.getConfig().getBoolean("tellPlayerNotRestored");
            if (tellPlayerNotRestored && !restorePlayerXP) {
                p.sendMessage(ChatColor.RED + "Unfortunately, we cannot restore your XP due to settings enforced by your Administrator.");
            }
        }
    }
    public class CommandReloadConfig implements CommandExecutor {
        // This method is called when somebody reloads the config for VoidKeepInv using /vkireload
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Config Reloaded!");
            return true;
        }
    }
}
