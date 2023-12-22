package it.einjojo.teleporter.core;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;

import java.util.UUID;

@Getter
@Setter
public class TeleporterImpl implements Teleporter {

    private final int id;
    private final String name;
    private final UUID owner;
    private final Component displayName;
    private final Location location;

    public TeleporterImpl(int id, String name, Component displayName, Location location, UUID owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.displayName = displayName;
        this.location = location;
    }
}
