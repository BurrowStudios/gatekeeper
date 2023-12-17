package org.burrow_studios.gatekeeper.net;

import com.sun.net.httpserver.HttpServer;
import org.burrow_studios.gatekeeper.Gatekeeper;
import org.burrow_studios.gatekeeper.net.handlers.EntityHandler;
import org.burrow_studios.gatekeeper.net.handlers.NotFoundHandler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final Logger LOG = Logger.getLogger(Server.class.getSimpleName());

    private final Gatekeeper gatekeeper;
    private final HttpServer httpServer;

    public Server(@NotNull Gatekeeper gatekeeper) throws IOException {
        this.gatekeeper = gatekeeper;

        LOG.log(Level.INFO, "Starting HTTP server on port 8080");
        this.httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

        this.httpServer.createContext("/", new NotFoundHandler(this));
        this.httpServer.createContext("/entities", new EntityHandler(this));

        LOG.log(Level.INFO, "Binding server...");
        this.httpServer.start();
        LOG.log(Level.INFO, "OK!");
    }

    public void stop() {
        LOG.log(Level.INFO, "Stopping HTTP server");
        this.httpServer.stop(4);
    }

    public @NotNull Gatekeeper getGatekeeper() {
        return gatekeeper;
    }
}
