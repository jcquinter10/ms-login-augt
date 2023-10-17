package com.globant.dojo.msloginaugt.service.impl;

import com.globant.dojo.msloginaugt.exception.DojoAugException;
import com.globant.dojo.msloginaugt.helper.constants.CodeConstants;
import com.globant.dojo.msloginaugt.helper.util.Util;
import com.globant.dojo.msloginaugt.model.dto.login.LoginDTO;
import com.globant.dojo.msloginaugt.model.persistence.User;
import com.globant.dojo.msloginaugt.repository.UserRepository;
import com.globant.dojo.msloginaugt.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> userAuthentication(LoginDTO loginDTO) throws DojoAugException {
        User user = userRepository.findByEmail(loginDTO.getEmail());
        if(user == null){
            throw Util.setDojoAugException(Collections.emptyList(), HttpStatus.BAD_REQUEST, CodeConstants.USER_NOT_EXIST);
        }else{
           if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
               throw Util.setDojoAugException(Collections.emptyList(), HttpStatus.BAD_REQUEST, CodeConstants.PASSWORD_NOT_VALID);
           }
        }
        return Optional.of(user);
    }

}
