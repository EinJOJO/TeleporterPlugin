package it.einjojo.teleporter.item;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

@Getter
public class ItemTeleporterSpawner {
    private final ItemStack itemStack;
    private static final Component displayName = Component.text("Teleporter Spawner");

    public ItemTeleporterSpawner() {
        this.itemStack = new ItemStack(Material.END_CRYSTAL);
        itemStack.editMeta(meta -> {
            meta.displayName(displayName);
            ArrayList<Component> lore = new ArrayList<>();
            lore.add(Component.text("Click to spawn a teleporter").color(NamedTextColor.DARK_PURPLE));
            meta.lore(lore);
        });
    }

    public static boolean isValid(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType() != Material.END_CRYSTAL) return false;
        if (itemStack.getItemMeta() == null) return false;
        if (itemStack.getItemMeta().displayName() == null) return false;

        return Objects.equals(itemStack.getItemMeta().displayName(), displayName);
    }
}
