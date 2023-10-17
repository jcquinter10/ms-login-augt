package com.globant.dojo.msloginaugt.exception;

import com.globant.dojo.msloginaugt.helper.constants.CodeConstants;
import com.globant.dojo.msloginaugt.helper.util.Util;
import com.globant.dojo.msloginaugt.model.dto.common.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class DojoAugExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { DojoAugException.class})
    protected ResponseEntity<Object> handleConflict(DojoAugException ex, WebRequest request) {
        return handleExceptionInternal(ex, GenericResponse.builder().data(ex.getErrors()).build(),
                new HttpHeaders(), ex.getCode() != null ? ex.getCode() : HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }


    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<String> messages = new ArrayList<>(constraintViolations.size());
        messages.addAll(constraintViolations.stream()
                .map(constraintViolation -> String.format("%s value '%s' %s", constraintViolation.getPropertyPath(),
                        constraintViolation.getInvalidValue(), constraintViolation.getMessage()))
                .collect(Collectors.toList()));

        DojoAugException apiError = Util.setDojoAugException(messages,
                HttpStatus.BAD_REQUEST, "");
        return new ResponseEntity<>(
                GenericResponse.builder().data(apiError.getErrors()).build(), apiError.getCode());
    }

    @ExceptionHandler(value = {MissingRequestHeaderException.class})
    protected ResponseEntity<Object> handleConflict(MissingRequestHeaderException ex, WebRequest request) {
        DojoAugException apiError = Util.setDojoAugException(Collections.emptyList(),
                HttpStatus.BAD_REQUEST,
                ex.getHeaderName());

        return handleExceptionInternal(ex, GenericResponse.builder().data(apiError.getErrors()).build(), new HttpHeaders(), apiError.getCode(), request);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleGeneralExceptions(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());

        DojoAugException apiError = Util.setDojoAugException(errors,
                HttpStatus.INTERNAL_SERVER_ERROR, "");

        return new ResponseEntity<>(GenericResponse.builder().data(apiError.getErrors()).build(), new HttpHeaders(), apiError.getCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleGeneralExceptions(RuntimeException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        DojoAugException apiError = Util.setDojoAugException(errors,
                HttpStatus.INTERNAL_SERVER_ERROR, "");

        return new ResponseEntity<>(GenericResponse.builder().data(apiError.getErrors()).build(), new HttpHeaders(), apiError.getCode());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField().concat(", "
                        .concat(Objects.requireNonNull(e.getDefaultMessage()))
                        .concat(CodeConstants.STRING_EMPTY)))
                .collect(Collectors.toList());


        DojoAugException apiError = Util.setDojoAugException(errors,
                HttpStatus.BAD_REQUEST, "");

        return new ResponseEntity<>(GenericResponse.builder().data(apiError.getErrors()).build(), new HttpHeaders(), apiError.getCode());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        DojoAugException apiError = Util.setDojoAugException(Collections.emptyList(),
                HttpStatus.BAD_REQUEST,
                "Required request body is missing");

        return new ResponseEntity<>(GenericResponse.builder().data(apiError.getErrors()).build(), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String name = ex.getParameterName();
        String message = name.concat(" parameter is missing");

        DojoAugException apiError = Util.setDojoAugException(Collections.emptyList(),
                HttpStatus.BAD_REQUEST,
                message);

        return new ResponseEntity<>(GenericResponse.builder().data(apiError.getErrors()).build(), headers, status);
    }

}
