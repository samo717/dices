package com.avaloq.dice.controller.handler;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorResponseHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleIllegalArgumentException(
        final HttpServletResponse response,
        final ConstraintViolationException e
    ) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Validation has failed: " + e.getMessage());
    }
}
