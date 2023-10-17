package com.globant.dojo.msloginaugt.model.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.globant.dojo.msloginaugt.helper.constants.CodeConstants;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDTO {

    @JsonProperty("email")
    @NotBlank(message = CodeConstants.MANDATORY_FIELD)
    @Email(message = CodeConstants.EMAIL_INVALID_FORMAT)
    private String email;

    @JsonProperty("password")
    @NotBlank(message = CodeConstants.MANDATORY_FIELD)
    private String password;
}
