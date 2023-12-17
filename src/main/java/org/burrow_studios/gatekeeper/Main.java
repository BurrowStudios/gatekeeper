package org.burrow_studios.gatekeeper;

import org.burrow_studios.gatekeeper.database.Database;
import org.burrow_studios.gatekeeper.net.Server;
import org.burrow_studios.gatekeeper.util.ResourceUtil;
import org.burrow_studios.gatekeeper.util.logging.LogUtil;

import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static {
        System.out.print("Starting Gatekeeper");
    }

    /** Static application version. */
    public static final String VERSION = ResourceUtil.getVersion();

    /** Directory in which the JAR ist located. */
    public static final File DIR;
    static {
        File f;
        try {
            f = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
        } catch (URISyntaxException e) {
            System.out.println("Failed to declare directory");
            throw new RuntimeException(e);
        }
        DIR = f;
    }

    private static final Logger LOG = Logger.getLogger("MAIN");

    /** JVM entrypoint */
    public static void main(String[] args) throws Exception {
        if (VERSION == null)
            throw new AssertionError("Unknown version");
        System.out.printf(" version %s...%n", VERSION);

        LogUtil.init();

        LOG.log(Level.INFO, "Creating config");
        ResourceUtil.createDefault("config.properties");
        Properties config = new Properties();
        config.load(new FileReader(new File(DIR, "config.properties")));

        String sqlHost     = config.getProperty("sql.host");
        String sqlPort     = config.getProperty("sql.port");
        String sqlDatabase = config.getProperty("sql.database");
        String sqlUser     = config.getProperty("sql.user");
        String sqlPass     = config.getProperty("sql.pass");

        Database database = new Database(sqlHost, sqlPort, sqlDatabase, sqlUser, sqlPass);
        Server   server   = new Server(database);
    }
}
