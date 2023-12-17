package org.burrow_studios.gatekeeper.net;

import com.sun.net.httpserver.HttpServer;
import org.burrow_studios.gatekeeper.Gatekeeper;
import org.burrow_studios.gatekeeper.net.handlers.EntityHandler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private final Gatekeeper gatekeeper;

    private final HttpServer httpServer;

    public Server(@NotNull Gatekeeper gatekeeper) throws IOException {
        this.gatekeeper = gatekeeper;

        this.httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

        this.httpServer.createContext("/", exchange -> {
            exchange.sendResponseHeaders(404, -1);
        });

        this.httpServer.createContext("/entities", new EntityHandler(this));

        this.httpServer.start();
    }

    public void stop() {
        this.httpServer.stop(4);
    }

    public Gatekeeper getGatekeeper() {
        return gatekeeper;
    }
}
