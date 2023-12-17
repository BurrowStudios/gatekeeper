package org.burrow_studios.gatekeeper.net;

import java.util.Objects;

public final class Response {
    public static final Response ERROR_BAD_REQUEST = new Response(400);
    public static final Response ERROR_NOT_FOUND = new Response(404);
    public static final Response ERROR_INTERNAL_SERVER_ERROR = new Response(500);
    public static final Response ERROR_NOT_IMPLEMENTED = new Response(501);

    private final int code;
    private final byte[] body;

    public Response(int code) {
        this(code, null);
    }

    public Response(int code, byte[] body) {
        this.code = code;
        this.body = Objects.requireNonNullElseGet(body, () -> new byte[0]);
    }

    public int getCode() {
        return code;
    }

    public byte[] getBody() {
        return body;
    }

    public int getBodyLength() {
        return body.length == 0 ? -1 : body.length;
    }

    /* - BUILDER METHODS - */

    public Response withBody(byte[] body) {
        return new Response(this.code, body);
    }
}
