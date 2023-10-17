package com.globant.dojo.msloginaugt.controller;

import com.globant.dojo.msloginaugt.exception.DojoAugException;
import com.globant.dojo.msloginaugt.model.dto.common.GenericResponse;
import com.globant.dojo.msloginaugt.model.dto.login.LoginDTO;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Login - Api",
        description = "User authentication.",
        externalDocs = @ExternalDocumentation(
                description = "For more information consult",
                url = "https://globant.open.com/"))
@Validated
@RequestMapping(path = "/${api.login.url}")
public interface LoginController {

    @Operation(summary = "User Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation, user authentication login", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "400", description = "Business error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = GenericResponse.class)))})
    @PostMapping()
    ResponseEntity<?> loginUser(@Valid @RequestBody LoginDTO loginDTO) throws DojoAugException;

}
