package org.cavebeetle.backend.rest;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GetHandler
{
    void handle(Path path, Connection connection, HttpServletRequest request, HttpServletResponse response);
}