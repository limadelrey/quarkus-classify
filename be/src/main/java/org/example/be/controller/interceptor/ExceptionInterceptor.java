package org.example.be.controller.interceptor;

import com.fasterxml.jackson.core.JsonParseException;
import org.example.be.model.json.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.NoSuchElementException;

@Provider
public class ExceptionInterceptor implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
         final Response.Status code;

        if (exception instanceof JsonParseException ||
                exception instanceof IllegalArgumentException) {
            code = Response.Status.BAD_REQUEST;
        } else if (exception instanceof NoSuchElementException) {
            code = Response.Status.NOT_FOUND;
        } else {
            code = Response.Status.INTERNAL_SERVER_ERROR;
        }

        return Response
                .status(code)
                .entity(new ErrorResponse(exception.getMessage()))
                .build();
    }

}
