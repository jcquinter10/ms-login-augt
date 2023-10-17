package com.globant.dojo.msloginaugt.controller;

import com.globant.dojo.msloginaugt.exception.DojoAugException;
import com.globant.dojo.msloginaugt.helper.constants.CodeConstants;
import com.globant.dojo.msloginaugt.model.dto.common.GenericResponse;
import com.globant.dojo.msloginaugt.model.dto.login.LoginDTO;
import com.globant.dojo.msloginaugt.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class LoginControllerImpl implements LoginController{

    private final LoginService loginService;

    @Override
    public ResponseEntity<?> loginUser(LoginDTO loginDTO) throws DojoAugException {
        log.info("User Authentication : {}", loginDTO);
        return loginService
                .userAuthentication(loginDTO)
                .map(ini -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(GenericResponse.builder().data(CodeConstants.AUTHENTICATED_USER).build()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(GenericResponse.builder().data(CodeConstants.BUSINESS_ERROR).build()));
    }
}
