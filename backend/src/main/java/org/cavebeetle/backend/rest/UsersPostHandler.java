package org.cavebeetle.backend.rest;

import static javax.json.Json.createReader;
import static org.cavebeetle.db.Tables.USERS;
import static org.jooq.SQLDialect.POSTGRES;

import java.io.IOException;
import java.sql.Connection;

import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cavebeetle.db.Tables;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public class UsersPostHandler
        implements
            PostHandler
{
    @Override
    public void handle(final Path path, final Connection connection, final HttpServletRequest request,
            final HttpServletResponse response)
    {
        try {
            final JsonReader jsonReader = createReader(request.getInputStream());
            final JsonObject user = jsonReader.readObject();
            final String name = user.getString("name");
            final String email = user.getString("email");
            DSLContext context = DSL.using(connection, POSTGRES);
            context.insertInto(USERS, USERS.USER_ID, USERS.NAME, USERS.PASSWORD, USERS.NAME)
            .values(1, "Orwell")
            .execute();
        } catch (final IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}