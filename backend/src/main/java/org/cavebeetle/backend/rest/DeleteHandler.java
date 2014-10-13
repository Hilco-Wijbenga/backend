package org.cavebeetle.backend.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface DeleteHandler
{
    void handle(Path path, HttpServletRequest request, HttpServletResponse response);
}