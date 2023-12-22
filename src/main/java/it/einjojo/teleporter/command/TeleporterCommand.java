package it.einjojo.teleporter.command;

import it.einjojo.teleporter.TeleporterPlugin;
import it.einjojo.teleporter.item.ItemShard;
import it.einjojo.teleporter.item.ItemTeleporterSpawner;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TeleporterCommand implements CommandExecutor, TabCompleter {

    public TeleporterCommand(TeleporterPlugin plugin) {
        plugin.getCommand("teleporter").setExecutor(this);
        plugin.getCommand("teleporter").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            sendGeneralUsage(sender);
            return true;
        }
        String subCommand = args[0];
        switch (subCommand) {
            case "give" -> giveItem(sender, args);
            case "list" -> listTeleporters(sender, args);
        }
        return true;

    }

    private void sendGeneralUsage(CommandSender sender) {
        TextColor gray = TextColor.color(0xAAAAAA);
        Component usage = Component.text("Usage: ").color(gray)
                .append(Component.newline())
                .append(Component.text("/teleporter give <spawner|shards> [amount] [player]").color(gray))
                .append(Component.newline())
                .append(Component.text("/teleporter list").color(gray));
        sender.sendMessage(usage);
    }

    private void giveItem(CommandSender sender, String[] args) {
        if (!sender.hasPermission("teleporter.give")) {
            sender.sendMessage(Component.text("Keine Rechte!"));
            return;
        }
        if (args.length < 2) {
            sendGeneralUsage(sender);
            return;
        }
        String item = args[1];
        int amount = getAmount(args);
        Player target = getTargetPlayer(sender, args);
        if (target == null) {
            sender.sendMessage(Component.text("Player not found"));
            return;
        }

        ItemStack is = null;
        switch (item) {
            case "spawner" -> {
                is = new ItemTeleporterSpawner().getItemStack();
            }
            case "shards" -> {
                is = new ItemShard().getItemStack();
            }

        }
        if (is == null) {
            sender.sendMessage(Component.text("Item not found"));
            return;
        }
        is.setAmount(amount);
        target.getInventory().addItem(is);
        target.sendMessage(Component.text("You received " + amount + " " + item));
    }

    Player getTargetPlayer(CommandSender sender, String[] args) {
        if (args.length >= 4) {
            return sender.getServer().getPlayer(args[3]);
        }
        if (sender instanceof Player) {
            return (Player) sender;
        }
        return null;
    }

    private int getAmount(String[] args) {
        if (args.length >= 3) {
            try {
                return Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                return 1;
            }
        }
        return 1;
    }

    private void listTeleporters(CommandSender sender, String[] args) {

    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length <= 1) {
            return List.of("give", "list");
        }
        if (args[0].equalsIgnoreCase("give") && args.length == 2) {
            return List.of("spawner", "shards");
        }
        return List.of();
    }
}
