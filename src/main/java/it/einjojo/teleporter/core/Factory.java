package it.einjojo.teleporter.core;

import it.einjojo.teleporter.TeleporterPlugin;
import it.einjojo.teleporter.item.ItemShard;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;

public class Factory {

    private final TeleporterPlugin plugin;
    private final NamespacedKey persistentIdKey;

    public Factory(TeleporterPlugin plugin) {
        this.plugin = plugin;
        this.persistentIdKey = new NamespacedKey(plugin, "teleporter_id");
    }

    public void spawn(Teleporter teleporter) {
        Location location = teleporter.getLocation();
        EnderCrystal crystal = (EnderCrystal) location.getWorld().spawnEntity(location, EntityType.ENDER_CRYSTAL);
        crystal.setInvulnerable(true);
        crystal.customName(teleporter.getDisplayName());
        crystal.setCustomNameVisible(true);
        PersistentDataContainer container = crystal.getPersistentDataContainer();
        container.set(persistentIdKey, PersistentDataType.INTEGER, teleporter.getId());
    }

    public void despawn(Teleporter teleporter) {
        Location location = teleporter.getLocation();
        location.getWorld().getNearbyEntities(location, 1, 1, 1).forEach(entity -> {
            Integer id = getTeleporterId(entity);
            if (id != null && id.equals(teleporter.getId())) {
                entity.remove();
            }
        });
    }

    /**
     * @param crystal the crystal to check
     * @return null if the crystal is not a teleporter
     */
    public Integer getTeleporterId(EnderCrystal crystal) {
        PersistentDataContainer container = crystal.getPersistentDataContainer();
        return container.get(persistentIdKey, PersistentDataType.INTEGER);
    }

    /**
     * @param entity the entity to check
     * @return null if the entity is not a teleporter
     */
    public Integer getTeleporterId(Entity entity) {
        if (entity instanceof EnderCrystal crystal) {
            return getTeleporterId(crystal);
        }
        return null;
    }


    public void bindItemStackToTeleporter(ItemStack is, @Nullable Teleporter teleporter) {
        if (is == null) return;
        PersistentDataContainer container = is.getItemMeta().getPersistentDataContainer();
        if (teleporter == null) {
            container.remove(persistentIdKey);
            is.editMeta(meta -> {
                meta.lore(ItemShard.unbindedLore());
            });
            return;
        }
        container.set(persistentIdKey, PersistentDataType.INTEGER, teleporter.getId());
        is.editMeta(meta -> {
            meta.lore(ItemShard.bindedLore(teleporter));
        });
    }
}
