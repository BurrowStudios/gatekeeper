package org.burrow_studios.gatekeeper.net.handlers;

import org.burrow_studios.gatekeeper.net.Response;
import org.burrow_studios.gatekeeper.net.RouteHandler;
import org.burrow_studios.gatekeeper.net.Server;
import org.jetbrains.annotations.NotNull;

public class NotFoundHandler extends RouteHandler {
    public NotFoundHandler(@NotNull Server server) {
        super(server);
    }

    @Override
    public @NotNull Response handle(String method, String path, String[] segments, String content) {
        return Response.ERROR_NOT_FOUND;
    }
}
