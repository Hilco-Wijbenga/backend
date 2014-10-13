package org.cavebeetle.backend.rest.user;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cavebeetle.backend.rest.GetHandlerWithData;
import org.cavebeetle.backend.rest.Path;

final class NameHandler
        implements
            GetHandlerWithData<UUID>
{
    @Override
    public void handle(final Path path, final UUID data, final HttpServletRequest request,
            final HttpServletResponse response)
    {
    }
}