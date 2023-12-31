package org.burrow_studios.gatekeeper.net.handlers;

import com.google.gson.JsonObject;
import org.burrow_studios.gatekeeper.net.Response;
import org.burrow_studios.gatekeeper.net.RouteHandler;
import org.burrow_studios.gatekeeper.net.Server;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EntityHandler extends RouteHandler {
    public EntityHandler(@NotNull Server server) {
        super(server);
    }

    @Override
    public @NotNull Response handle(String method, String path, String[] segments) throws Exception {
        assert segments.length > 0;

        if (segments.length == 1) {
            assert Objects.equals(segments[0], "entities");

            return Response.ofJson(200, getDatabase().getEntities());
        }

        String idStr = segments[1];
        long   id;

        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            return Response.ERROR_BAD_REQUEST.withBody("Invalid id format".getBytes());
        }

        JsonObject entity = getDatabase().getEntity(id);

        if (entity == null)
            return new Response(204);
        return Response.ofJson(200, entity);
    }
}
