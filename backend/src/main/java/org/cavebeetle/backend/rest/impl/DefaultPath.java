package org.cavebeetle.backend.rest.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.ImmutableList.copyOf;
import static java.util.Arrays.asList;

import java.util.List;

import org.cavebeetle.backend.rest.Path;

public final class DefaultPath
        implements
            Path
{
    public static final class DefaultBuilder
            implements
                Builder
    {
        public Path newPath(final List<String> pathParts)
        {
            return new DefaultPath(pathParts);
        }

        public Path newPath(final String[] pathParts)
        {
            return newPath(asList(pathParts));
        }

        public Path getEmptyPath()
        {
            return EMPTY_PATH;
        }
    }

    private final String head;
    private final Path   tail;

    public DefaultPath(final List<String> pathParts)
    {
        checkArgument(pathParts != null && !pathParts.isEmpty(), "Missing path parts.");
        head = pathParts.get(0);
        if (pathParts.size() == 1)
            tail = EMPTY_PATH;
        else
            tail = new DefaultPath(copyOf(pathParts.subList(1, pathParts.size())));
    }

    public boolean isEmpty()
    {
        return false;
    }

    public String getHead()
    {
        return head;
    }

    public Path getTail()
    {
        return tail;
    }
}