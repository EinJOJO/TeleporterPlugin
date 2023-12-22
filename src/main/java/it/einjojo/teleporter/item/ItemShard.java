package it.einjojo.teleporter.item;

import it.einjojo.teleporter.core.Teleporter;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemShard {

    private final ItemStack itemStack;
    private static final Component itemTitle = Component.text("Teleporter Shard").color(NamedTextColor.LIGHT_PURPLE).decorate(TextDecoration.BOLD);

    public ItemShard() {
        this.itemStack = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.displayName(itemTitle);
        itemMeta.lore(unbindedLore());
        itemStack.setItemMeta(itemMeta);
    }

    public static boolean isShard(ItemStack is) {
        if (is == null) return false;
        if (is.getType() != Material.AMETHYST_SHARD) return false;
        if (!is.hasItemMeta()) return false;
        ItemMeta itemMeta = is.getItemMeta();
        Component displayName = itemMeta.displayName();
        if (displayName == null) return false;
        return displayName.equals(itemTitle);
    }


    public static List<Component> unbindedLore() {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Teleporter: ").color(NamedTextColor.GRAY).append(Component.text("-").color(NamedTextColor.GRAY)));
        lore.add(Component.text("Click to bind this shard to the teleporter").color(NamedTextColor.DARK_PURPLE));
        return lore;
    }

    public static List<Component> bindedLore(Teleporter teleporter) {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Teleporter: ").color(NamedTextColor.GRAY).append(teleporter.getDisplayName()));
        lore.add(Component.text("").color(NamedTextColor.DARK_PURPLE));
        return lore;
    }

}
