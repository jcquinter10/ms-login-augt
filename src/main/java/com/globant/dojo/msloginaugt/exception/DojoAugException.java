package com.globant.dojo.msloginaugt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DojoAugException extends Exception{

    private final HttpStatus code;
    private final List<String> errors;

    public DojoAugException() {
        super();
        this.code = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errors = new ArrayList<>();
    }

    public DojoAugException(String msg, HttpStatus code, List<String> errors) {
        super(msg);
        this.code = code;
        this.errors = errors;
    }

    public DojoAugException(String msg, Exception e, HttpStatus code, List<String> errors) {
        super(msg, e);
        this.code = code;
        this.errors = errors;
    }
}
