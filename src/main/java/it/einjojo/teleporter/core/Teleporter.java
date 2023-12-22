package it.einjojo.teleporter.core;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;

import java.util.UUID;

public interface Teleporter {
    int getId();

    UUID getOwner();

    String getName();

    Component getDisplayName();

    Location getLocation();
}
