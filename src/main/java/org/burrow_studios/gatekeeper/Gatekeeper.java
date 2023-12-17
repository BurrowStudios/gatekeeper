package org.burrow_studios.gatekeeper;

import org.burrow_studios.gatekeeper.database.Database;
import org.burrow_studios.gatekeeper.net.Server;
import org.burrow_studios.gatekeeper.util.ResourceUtil;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Gatekeeper {
    public static final Logger LOG = Logger.getLogger("MAIN");

    private final Properties config = new Properties();
    private final Database database;
    private final Server server;

    Gatekeeper() throws Exception {
        LOG.log(Level.INFO, "Creating config");
        ResourceUtil.createDefault("config.properties");
        this.config.load(new FileReader(new File(Main.DIR, "config.properties")));

        final String sqlHost     = config.getProperty("sql.host");
        final String sqlPort     = config.getProperty("sql.port");
        final String sqlDatabase = config.getProperty("sql.database");
        final String sqlUser     = config.getProperty("sql.user");
        final String sqlPass     = config.getProperty("sql.pass");

        LOG.log(Level.INFO, "Starting database");
        this.database = new Database(sqlHost, sqlPort, sqlDatabase, sqlUser, sqlPass);

        LOG.log(Level.INFO, "Starting API server");
        this.server = new Server(this);
    }

    public Database getDatabase() {
        return database;
    }

    public Server getServer() {
        return server;
    }

    void stop() throws Exception {
        LOG.log(Level.WARNING, "Shutting down");
        server.stop();
        database.stop();
        LOG.log(Level.INFO, "OK bye");
    }
}
