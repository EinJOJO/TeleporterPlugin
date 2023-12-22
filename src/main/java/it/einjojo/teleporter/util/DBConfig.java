package it.einjojo.teleporter.util;

import it.einjojo.teleporter.db.DatabaseCredentials;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class DBConfig implements DatabaseCredentials {

    private final FileConfiguration config;

    public DBConfig(File file) {
        config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public String getHost() {
        return config.getString("host");
    }

    @Override
    public int getPort() {
        return config.getInt("port");
    }

    @Override
    public String getDatabase() {
        return config.getString("database");
    }

    @Override
    public String getUsername() {
        return config.getString("username");
    }

    @Override
    public String getPassword() {
        return config.getString("password");
    }

    @Override
    public int getConnectionTimeout() {
        return config.getInt("connectionTimeout");
    }
}
