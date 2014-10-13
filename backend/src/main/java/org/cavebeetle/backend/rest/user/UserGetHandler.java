package org.cavebeetle.backend.rest.user;

import static org.cavebeetle.db.Tables.USERS;
import static org.jooq.SQLDialect.POSTGRES;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cavebeetle.backend.rest.GetHandler;
import org.cavebeetle.backend.rest.GetHandlerWithData;
import org.cavebeetle.backend.rest.Path;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import com.google.common.collect.Maps;

public final class UserGetHandler
        implements
            GetHandler
{
    private final Map<String, GetHandlerWithData<UUID>> map;

    public UserGetHandler()
    {
        map = Maps.newHashMap();
        map.put("email", new EmailHandler());
        map.put("name", new NameHandler());
    }

    @Override
    public void handle(final Path path, final Connection connection, final HttpServletRequest request,
            final HttpServletResponse response)
    {
        final UUID userId = UUID.fromString(path.getHead());
        final Path tail = path.getTail();
        if (tail.isEmpty()) {
            final DSLContext create = DSL.using(connection, POSTGRES);
            final Result<Record> result = create
                    .select()
                    .from(USERS)
                    .where(USERS.USER_ID.eq(userId))
                    .fetch();
            try {
                response.getWriter().write(result.formatJSON());
            } catch (final IOException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        } else if (!map.containsKey(tail.getHead()))
            throw new IllegalStateException();
        else
            map.get(tail.getHead()).handle(tail.getTail(), userId, request, response);
    }
}