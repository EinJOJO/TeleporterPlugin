package it.einjojo.teleporter.db;

import it.einjojo.teleporter.core.Teleporter;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeleportDatabase {

    HikariCP hikariCP;

    public TeleportDatabase(HikariCP hikariCP) {
        this.hikariCP = hikariCP;
    }


    public void createTeleporter(Teleporter teleporter) {
        try (Connection connection = hikariCP.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO teleporter (name, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                Location location = teleporter.getLocation();
                ps.setString(1, teleporter.getName());
                ps.setString(2, location.getWorld().getName());
                ps.setDouble(3, location.getX());
                ps.setDouble(4, location.getY());
                ps.setDouble(5, location.getZ());
                ps.setFloat(6, location.getYaw());
                ps.setFloat(7, location.getPitch());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTeleporter(Teleporter teleporter) {
        try (Connection connection = hikariCP.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("DELETE FROM teleporter WHERE id = ?")) {
                ps.setInt(1, teleporter.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
