package it.einjojo.teleporter.util;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerChatInput implements Listener {

    private final JavaPlugin plugin;
    private final BukkitTask taskID;
    private final Consumer<String> callback;
    private final UUID playerUUID;

    private static final Map<UUID, PlayerChatInput> inputs = new HashMap<>();
    private static final Component info = MiniMessage.miniMessage().deserialize("<gray>Gib <red>cancel <gray>ein, um abzubrechen.");

    public PlayerChatInput(JavaPlugin plugin, Player player, Component title, Consumer<String> callback) {
        this.plugin = plugin;

        this.taskID = new BukkitRunnable() {
            @Override
            public void run() {
                player.showTitle(Title.title(title, info));
            }
        }.runTaskTimer(plugin, 0, 20);

        this.playerUUID = player.getUniqueId();
        this.callback = callback;

        register();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onChat(AsyncChatEvent e) {
        String input = e.originalMessage().toString();
        Player player = e.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (!inputs.containsKey(playerUUID)) {
            return;
        }
        PlayerChatInput current = inputs.get(playerUUID);
        e.setCancelled(true);
        current.taskID.cancel();
        current.unregister();
        player.resetTitle();
        if (input.equalsIgnoreCase("cancel")) {
            input = null;

        }
        String finalInput = input;
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> current.callback.accept(finalInput), 3);
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        UUID uuid = player.getUniqueId();

        if (!inputs.containsKey(uuid)) {
            return;
        }
        PlayerChatInput current = inputs.get(uuid);
        current.taskID.cancel();
        current.unregister();
        player.resetTitle();
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> current.callback.accept(null), 3);
        aborted(player);
    }


    private void aborted(Player player) {
        Component aborted = Component.text("Abgebrochen!").color(NamedTextColor.RED);
        player.sendActionBar(aborted);
    }

    private void register() {
        inputs.put(this.playerUUID, this);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private void unregister() {
        inputs.remove(this.playerUUID);
        HandlerList.unregisterAll(this);
    }


}