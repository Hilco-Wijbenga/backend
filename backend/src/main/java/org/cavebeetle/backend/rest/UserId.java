package org.cavebeetle.backend.rest;

public final class UserId
{
    private final String userId;

    public UserId(final String userId)
    {
        this.userId = userId;
    }

    @Override
    public String toString()
    {
        return userId;
    }
}