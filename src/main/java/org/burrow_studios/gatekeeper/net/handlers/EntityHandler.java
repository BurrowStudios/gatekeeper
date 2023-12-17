package org.burrow_studios.gatekeeper.net.handlers;

import org.burrow_studios.gatekeeper.database.Database;
import org.burrow_studios.gatekeeper.net.Response;
import org.burrow_studios.gatekeeper.net.RouteHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EntityHandler extends RouteHandler {
    private final Database database;

    public EntityHandler(@NotNull Database database) {
        this.database = database;
    }

    @Override
    public @NotNull Response handle(String method, String path, String[] segments) {
        assert segments.length > 0;

        if (segments.length == 1) {
            assert Objects.equals(segments[0], "entities");

            // TODO: return list of all known entities
            return Response.ERROR_NOT_IMPLEMENTED;
        }

        String idStr = segments[1];
        long   id;

        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            return Response.ERROR_BAD_REQUEST.withBody("Invalid id format".getBytes());
        }

        // TODO: return entity
        return Response.ERROR_NOT_IMPLEMENTED;
    }
}
