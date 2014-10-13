package org.cavebeetle.backend.rest;

import java.util.List;

public interface Path
{
    public interface Builder
    {
        Path newPath(List<String> pathParts);

        Path newPath(String[] pathParts);

        Path getEmptyPath();
    }

    Path EMPTY_PATH = new Path()
                    {
                        public boolean isEmpty()
                        {
                            return true;
                        }

                        public String getHead()
                        {
                            throw new IllegalStateException("This is an empty path.");
                        }

                        public Path getTail()
                        {
                            throw new IllegalStateException("This is an empty path.");
                        }
                    };

    boolean isEmpty();

    String getHead();

    Path getTail();
}