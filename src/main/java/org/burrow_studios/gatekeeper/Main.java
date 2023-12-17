package org.burrow_studios.gatekeeper;

import org.burrow_studios.gatekeeper.util.ResourceUtil;
import org.burrow_studios.gatekeeper.util.logging.LogUtil;

import java.io.File;
import java.net.URISyntaxException;

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

    /** JVM entrypoint */
    public static void main(String[] args) throws Exception {
        if (VERSION == null)
            throw new AssertionError("Unknown version");
        System.out.printf(" version %s...%n", VERSION);

        LogUtil.init();

        Gatekeeper gatekeeper = new Gatekeeper();
    }
}
