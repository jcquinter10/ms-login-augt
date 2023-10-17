package com.globant.dojo.msloginaugt.helper.util;

import com.globant.dojo.msloginaugt.exception.DojoAugException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Util {

    public static DojoAugException setDojoAugException(List<String> errors, HttpStatus code, String message){
        List<String> messages = new ArrayList<>();
        if(errors.isEmpty() && !message.isEmpty()){
            messages.add(message);
        }else{
            messages.addAll(errors);
        }
        return new DojoAugException(code, messages);
    }

}
