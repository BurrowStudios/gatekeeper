package org.burrow_studios.gatekeeper.net;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class RouteHandler implements HttpHandler {
    protected static final Logger LOG = Logger.getLogger(RouteHandler.class.getSimpleName());

    @Override
    public final void handle(HttpExchange exchange) throws IOException {
        final String   requestMethod;
        final String   requestPath;
        final String[] requestPathSegments;

        try {
            requestMethod       = exchange.getRequestMethod();
            requestPath         = exchange.getRequestURI().getPath();
            requestPathSegments = requestPath.split("/");
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Encountered an exception when attempting to prepare a request", e);
            exchange.sendResponseHeaders(Response.ERROR_INTERNAL_SERVER_ERROR.getCode(), -1);
            return;
        }

        Response response = handle0(requestMethod, requestPath, requestPathSegments);

        exchange.sendResponseHeaders(response.getCode(), response.getBodyLength());
        exchange.getResponseBody().write(response.getBody());

        exchange.close();
    }

    private @NotNull Response handle0(String method, String path, String[] pathSegments) {
        try {
            return this.handle(method, path, pathSegments);
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Encountered an exception when attempting to prepare a request", e);
            return Response.ERROR_INTERNAL_SERVER_ERROR;
        }
    }

    public abstract @NotNull Response handle(String method, String path, String[] segments);
}