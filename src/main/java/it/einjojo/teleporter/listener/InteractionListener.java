package it.einjojo.teleporter.listener;

import it.einjojo.teleporter.TeleporterPlugin;
import it.einjojo.teleporter.core.Teleporter;
import it.einjojo.teleporter.item.ItemShard;
import it.einjojo.teleporter.item.ItemTeleporterSpawner;
import it.einjojo.teleporter.util.PlayerChatInput;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class InteractionListener implements Listener {

    private final TeleporterPlugin plugin;
    private static final Component PROMPT = MiniMessage.miniMessage().deserialize("<rainbow>Teleporter benennen");

    public InteractionListener(TeleporterPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void placeTeleporter(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (!event.getAction().isRightClick()) return;
        if (!Objects.equals(event.getHand(), EquipmentSlot.HAND)) return;
        if (event.getClickedBlock() == null) return;
        if (!ItemTeleporterSpawner.isValid(event.getItem())) return;
        Location location = event.getClickedBlock().getLocation().add(0.5, 1, 0.5);

        new PlayerChatInput(plugin, event.getPlayer(), PROMPT, (name) -> {
            Teleporter teleporter = plugin.getManager().createTeleporter(name, event.getPlayer().getUniqueId(), location);
            plugin.getFactory().spawn(teleporter);

            event.getPlayer().sendMessage(Component.text("Teleporter erstellt!"));
        });
    }

    @EventHandler
    public void onRightClickTeleporter(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof EnderCrystal)) return;
        if (event.getPlayer().getInventory().getItemInMainHand().isEmpty()) return;
        ItemStack is = event.getPlayer().getInventory().getItemInMainHand();
        if (!ItemShard.isShard(is)) return;
        EnderCrystal crystal = (EnderCrystal) event.getRightClicked();
        Integer id = plugin.getFactory().getTeleporterId(crystal);
        if (id == null) return;

        Teleporter teleporter = plugin.getManager().getTeleporter(id);
        if (teleporter == null) return;
        plugin.getFactory().bindItemStackToTeleporter(is, teleporter);
        event.getPlayer().sendMessage(Component.text("Teleporter gebunden!"));

    }


}
