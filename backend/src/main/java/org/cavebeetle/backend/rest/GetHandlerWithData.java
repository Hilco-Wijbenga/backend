package org.cavebeetle.backend.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GetHandlerWithData<D>
{
    void handle(Path path, D data, HttpServletRequest request, HttpServletResponse response);
}