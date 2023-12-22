package it.einjojo.teleporter.db;

public interface DatabaseCredentials {
    String getHost();
    int getPort();
    String getDatabase();
    String getUsername();
    String getPassword();
    int getConnectionTimeout();
}