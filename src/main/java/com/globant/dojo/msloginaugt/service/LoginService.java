package com.globant.dojo.msloginaugt.service;

import com.globant.dojo.msloginaugt.exception.DojoAugException;
import com.globant.dojo.msloginaugt.model.dto.login.LoginDTO;
import com.globant.dojo.msloginaugt.model.persistence.User;

import java.util.Optional;


public interface LoginService {

    Optional<User> userAuthentication(LoginDTO loginDTO)throws DojoAugException;

}
