package org.example.be.controller.interceptor;

import org.example.be.model.json.ErrorResponse;

import javax.annotation.Priority;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;

@Provider
public class ValidationInterceptor implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        final String exceptionMessage = exception
                .getConstraintViolations()
                .stream()
                .map(v -> v.getMessage())
                .collect(Collectors.joining("; "));

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(exceptionMessage))
                .build();
    }

}