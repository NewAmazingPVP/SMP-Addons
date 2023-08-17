package mc.untitled;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.OptionalDouble;

public final class Untitled extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }


    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event) {
        AnvilInventory anvilInventory = event.getInventory();
        if((!(anvilInventory.getRenameText() == null)) && anvilInventory.getSecondItem() == null) {
            event.getInventory().setRepairCost(1);
        }
    }


    /*@EventHandler
    public void onDragonEggPlace(InventoryClickEvent event) {
        if ((event.getInventory().getType().toString().contains("ENDER_CHEST") ||
                event.getInventory().getType().toString().contains("CHEST") ||
                event.getInventory().getType().toString().contains("SHULKER_BOX")) &&
                event.getCurrentItem().getType().toString().contains("DRAGON_EGG")) {
            Player player = (Player) event.getView().getPlayer();
            player.sendMessage(ChatColor.RED + "You can only keep dragon egg in your inventory!");
            player.sendTitle(ChatColor.RED + "WARNING!", ChatColor.RED + "You can only keep dragon egg in your inventory!");
            event.setCancelled(true);
        }
    }*/

    @EventHandler
    public void playerChat(PlayerChatEvent event) {
        if ((event.getMessage().contains("lag") || event.getMessage().contains("tps")) &&
                !(
                        event.getMessage().contains("not lagging") ||
                                event.getMessage().contains("not laggy") ||
                                event.getMessage().contains("didnt lag") ||
                                event.getMessage().contains("llager") ||
                                event.getMessage().contains("https")
                )) {
            OptionalDouble tpsTest = Arrays.stream(getServer().getTPS()).findFirst();
            double tps = tpsTest.orElse(20.0);
            if (tps > 20.0) {
                tps = 20.0;
            }
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            double finalTps = Double.parseDouble(decimalFormat.format(tps));
            Bukkit.getScheduler().runTaskLater(this, new Runnable() {
                @Override
                public void run() {
                    if (finalTps > 19.93) {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            p.sendMessage("The server currently has " + ChatColor.AQUA + finalTps + ChatColor.WHITE + " tps and is not lagging. Check your wifi/ping instead " + ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.WHITE + ". Decrease your render/simulation distance and its recommended for you to use fabously optimized modpack for more performance and less client lag.");
                        }
                    } else {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            p.sendMessage("The server currently has " + ChatColor.RED + finalTps + "tps and could be lagging");
                        }
                    }
                }
            }, 20);
        }
    }



   /*@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Location searchLocation = player.getLocation();
        System.out.println("LOOP ON");
        Location portalLocation = findNearestPortal(searchLocation);
        System.out.println("test ON");

        Bukkit.getScheduler().runTask(Untitled.this, () -> {
            System.out.println("TASK ON");
            if (portalLocation != null) {
                System.out.println("FOUND PORTAL");
                player.sendMessage("The nearest portal is located at: " +
                        "X: " + portalLocation.getBlockX() +
                        " Y: " + portalLocation.getBlockY() +
                        " Z: " + portalLocation.getBlockZ());
            } else {
                player.sendMessage("No Nether portal found nearby.");
            }
        });
    }

    public Location findNearestPortal(Location location) {
        int searchDistance = 2048;

        for (int x = -searchDistance; x <= searchDistance; x++) {
            for (int y = -searchDistance; y <= searchDistance; y++) {
                for (int z = -searchDistance; z <= searchDistance; z++) {
                    Location targetLocation = location.clone().add(x, y, z);
                    Block block = targetLocation.getBlock();

                    if (block.getType() == Material.NETHER_PORTAL) {
                        return block.getLocation();
                    }
                }
            }
        }

        return null;
    }*/
}
