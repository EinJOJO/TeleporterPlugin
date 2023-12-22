package it.einjojo.teleporter.core;

import it.einjojo.teleporter.TeleporterPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;

import java.util.UUID;

public class Manager {

    TeleporterPlugin plugin;

    public Manager(TeleporterPlugin plugin) {
        this.plugin = plugin;
    }

     Teleporter test;

    public Teleporter createTeleporter(String name, UUID owner, Location location) {
        test = new TeleporterImpl(1, name, Component.text(name).color(NamedTextColor.RED), location, owner);
        return test;
    }

    public Teleporter getTeleporter(int id) {
        return test;
    }
}
