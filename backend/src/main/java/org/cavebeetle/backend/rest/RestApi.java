package org.cavebeetle.backend.rest;

import static com.google.common.collect.Maps.newHashMap;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cavebeetle.backend.rest.user.UserGetHandler;
import org.cavebeetle.backend.rest.users.UsersGetHandler;
import org.postgresql.Driver;

/**
 * /users:GET  ?offset={user-id}&count=m
 * /users:POST
 *     {
 *         name = "...";
 *         email = "...";
 *     }
 * /user/{user-id}:GET
 * /user/{user-id}/name:GET
 * /user/{user-id}/name:POST
 *     {
 *         name = "...";
 *     }
 * /user/{user-id}/email:GET
 * /user/{user-id}/email:POST
 *     {
 *         email = "...";
 *     }
 */
public final class RestApi
        extends
            HttpServlet
{
    private static final long                serialVersionUID = -8846872263354306141L;
    private final Path.Builder               pathBuilder;
    private Connection                       connection;
    private final Map<String, GetHandler>    getHandlers;
    private final Map<String, PostHandler>   postHandlers;
    private final Map<String, PutHandler>    putHandlers;
    private final Map<String, DeleteHandler> deleteHandlers;

    public RestApi(final Path.Builder pathBuilder)
    {
        this.pathBuilder = pathBuilder;
        getHandlers = newHashMap();
        postHandlers = newHashMap();
        putHandlers = newHashMap();
        deleteHandlers = newHashMap();
        final String userName = "hilco";
        final String password = "slugathon";
        final String url = "jdbc:postgresql:backend";
        System.out.println(Driver.class + ": version " + Driver.getVersion() + ".");
        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (final SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public void init()
            throws ServletException
    {
        getHandlers.put("users", new UsersGetHandler());
        getHandlers.put("user", new UserGetHandler());
        postHandlers.put("users",  new UsersPostHandler());
    }

    @Override
    public void destroy()
    {
        try {
            connection.close();
        } catch (final SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public void doGet(final ServletRequest request, final ServletResponse response)
            throws ServletException,
                IOException
    {
        final HttpServletRequest request_ = (HttpServletRequest) request;
        final HttpServletResponse response_ = (HttpServletResponse) response;
        final List<String> urlParts = asList(request_.getPathInfo().split("/"));
        final Path path = pathBuilder.newPath(urlParts);
        if (!getHandlers.containsKey(path.getHead()))
            response_.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else
            getHandlers.get(path.getHead()).handle(path.getTail(), connection, request_, response_);
    }
}