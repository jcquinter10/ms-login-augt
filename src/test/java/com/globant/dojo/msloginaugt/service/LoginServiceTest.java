package com.globant.dojo.msloginaugt.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.globant.dojo.msloginaugt.common.Util;
import com.globant.dojo.msloginaugt.config.MapperResolver;
import com.globant.dojo.msloginaugt.exception.DojoAugException;
import com.globant.dojo.msloginaugt.model.dto.login.LoginDTO;
import com.globant.dojo.msloginaugt.model.dto.login.UserDto;
import com.globant.dojo.msloginaugt.model.mapper.UserMapper;
import com.globant.dojo.msloginaugt.model.persistence.User;
import com.globant.dojo.msloginaugt.repository.UserRepository;
import com.globant.dojo.msloginaugt.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ImportAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
@ActiveProfiles("test")
class LoginServiceTest {

    @Autowired
    private LoginServiceImpl loginService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Spy
    private UserMapper userMapper = MapperResolver.getMapper(UserMapper.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    @Test
    void userAuthenticationSuccessTest() throws Exception{
        // Arrange
        LoginDTO request = mapper.readValue(Util.readFile(Util.LOGIN_DTO), LoginDTO.class);
        UserDto userDto = mapper.readValue(Util.readFile(Util.USER_DTO), UserDto.class);
        User user = userMapper.toEntity(userDto);

        doReturn(user).when(userRepository).
                findByEmail(any());

        doReturn(Boolean.TRUE).when(passwordEncoder).matches(any(),any());

        // Act
        Optional<User> response = loginService.userAuthentication(request);

        // Assert
        assertEquals(Boolean.TRUE, response.isPresent());
        assertEquals(user.getName(), response.get().getName());
    }

    @Test
    void userAuthenticationExceptionTest() throws IOException {
        // Arrange
        LoginDTO request = mapper.readValue(Util.readFile(Util.LOGIN_DTO), LoginDTO.class);
        request.setEmail("test1@globant.com");

        doThrow(RuntimeException.class).when(userRepository)
                .findByEmail(any());

        // Act & Assert
        assertThrows(Exception.class,
                () -> loginService.userAuthentication(request));
    }

    @Test
    void userAuthenticationUserNotExistBadRequestTest() throws IOException {
        // Arrange
        LoginDTO request = mapper.readValue(Util.readFile(Util.LOGIN_DTO), LoginDTO.class);
        request.setEmail("test@globant.com");
        doReturn(null).when(userRepository).
                findByEmail(any());

        // Act & Assert
        assertThrows(DojoAugException.class,
                () -> loginService.userAuthentication(request));
    }

    @Test
    void userAuthenticationErrorPasswordBadRequestTest() throws IOException {
        // Arrange
        LoginDTO request = mapper.readValue(Util.readFile(Util.LOGIN_DTO), LoginDTO.class);
        UserDto userDto = mapper.readValue(Util.readFile(Util.USER_DTO), UserDto.class);
        User user = userMapper.toEntity(userDto);

        doReturn(user).when(userRepository).
                findByEmail(any());

        doReturn(Boolean.FALSE).when(passwordEncoder).matches(any(),any());

        // Act & Assert
        assertThrows(DojoAugException.class,
                () -> loginService.userAuthentication(request));
    }

}
