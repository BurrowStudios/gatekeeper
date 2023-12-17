package org.burrow_studios.gatekeeper.net;

import com.sun.net.httpserver.HttpServer;
import org.burrow_studios.gatekeeper.database.Database;
import org.burrow_studios.gatekeeper.net.handlers.EntityHandler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private final Database database;

    private final HttpServer httpServer;

    public Server(@NotNull Database database) throws IOException {
        this.database = database;

        this.httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

        this.httpServer.createContext("/", exchange -> {
            exchange.sendResponseHeaders(404, -1);
        });

        this.httpServer.createContext("/entities", new EntityHandler(database));

        this.httpServer.start();
    }

    public void stop() {
        this.httpServer.stop(4);
    }
}
