package com.appchallengers.webservice.endpoint.error_handling;

import com.google.gson.Gson;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

public class CommonExceptionHandler extends Exception {

    public CommonExceptionHandler(String message) {
        super(message);
    }

    @Provider
    public static class CommonExceptionHandlerMapper implements ExceptionMapper<CommonExceptionHandler> {
        public Response toResponse(CommonExceptionHandler commonExceptionHandler) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new Gson().toJson(new Error(Integer.parseInt(commonExceptionHandler.getMessage())))).type(MediaType.APPLICATION_JSON).build();
        }
    }
}
