package com.globant.dojo.msloginaugt.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.globant.dojo.msloginaugt.common.Util;
import com.globant.dojo.msloginaugt.exception.DojoAugException;
import com.globant.dojo.msloginaugt.helper.constants.CodeConstants;
import com.globant.dojo.msloginaugt.model.dto.common.GenericResponse;
import com.globant.dojo.msloginaugt.model.dto.login.LoginDTO;
import com.globant.dojo.msloginaugt.model.persistence.User;
import com.globant.dojo.msloginaugt.repository.UserRepository;
import com.globant.dojo.msloginaugt.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ImportAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
@ActiveProfiles("test")
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LoginService loginService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    void loginUserSuccess() throws Exception {
        // Arrange
        LoginDTO loginDTO = mapper.readValue(Util.readFile(Util.LOGIN_DTO), LoginDTO.class);
        String requestJson = mapper.writeValueAsString(loginDTO);

        doReturn(Optional.of(User.builder().id(3907L).build())).when(loginService).userAuthentication(any());

        // Act
        MvcResult result = mockMvc.perform(post(Util.LOGIN_PATH)
                .contentType(APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON)).andReturn();

        // Assert
        String responseJson = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        GenericResponse response = mapper.readValue(responseJson, GenericResponse.class);
        assertEquals(CodeConstants.AUTHENTICATED_USER, response.getData());
    }


    @Test
    void loginUserBadRequest() throws Exception {
        //Arrange
        LoginDTO loginDTO = mapper.readValue(Util.readFile(Util.LOGIN_DTO_BAD), LoginDTO.class);
        String requestJson = mapper.writeValueAsString(loginDTO);

        // Act && assert
        mockMvc.perform(post(Util.LOGIN_PATH)
                .contentType(APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    void loginUserServerError() throws Exception {
        //Arrange
        LoginDTO loginDTO = mapper.readValue(Util.readFile(Util.LOGIN_DTO), LoginDTO.class);
        String requestJson = mapper.writeValueAsString(loginDTO);

        DojoAugException productException = new DojoAugException(
                HttpStatus.INTERNAL_SERVER_ERROR, List.of(Util.EXCEPTION_MESSAGE));

        doThrow(productException).when(loginService).userAuthentication(any());

        // Act && assert
        mockMvc.perform(post(Util.LOGIN_PATH)
                .contentType(APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().is5xxServerError());
    }


}
