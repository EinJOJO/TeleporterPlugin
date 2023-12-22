package it.einjojo.teleporter;

import it.einjojo.teleporter.command.TeleporterCommand;
import it.einjojo.teleporter.db.HikariCP;
import it.einjojo.teleporter.listener.InteractionListener;
import it.einjojo.teleporter.core.Factory;
import it.einjojo.teleporter.core.Manager;
import it.einjojo.teleporter.util.DBConfig;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class TeleporterPlugin extends JavaPlugin {

    @Getter
    private HikariCP hikariCP;

    @Getter
    private Factory factory;

    @Getter
    private Manager manager;

    @Override
    public void onLoad() {
        saveResource("database.yml", false);
    }

    @Override
    public void onEnable() {
        loadDatabase();
        new TeleporterCommand(this);
        new InteractionListener(this);
        factory = new Factory(this);
        manager = new Manager(this);
    }

    private void loadDatabase() {
        File file = new File(getDataFolder(), "database.yml");
        if (!file.exists()) {
            getLogger().severe("Database file not found!");
            return;
        }
        DBConfig config = new DBConfig(file);
        hikariCP = new HikariCP(config);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
