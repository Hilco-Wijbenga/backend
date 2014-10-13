package org.cavebeetle.backend.rest.users;

import static org.cavebeetle.db.Tables.USERS;
import static org.jooq.SQLDialect.POSTGRES;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cavebeetle.backend.rest.GetHandler;
import org.cavebeetle.backend.rest.Path;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

public final class UsersGetHandler
        implements
            GetHandler
{
    @Override
    public void handle(final Path path, final Connection connection, final HttpServletRequest request,
            final HttpServletResponse response)
    {
        final DSLContext context = DSL.using(connection, POSTGRES);
        final Result<Record> result = context
                .select()
                .from(USERS)
                .fetch();
        try {
            response.getWriter().write(result.formatJSON());
        } catch (final IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

    }
}