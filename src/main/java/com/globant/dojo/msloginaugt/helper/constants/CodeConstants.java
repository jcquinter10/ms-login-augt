package com.globant.dojo.msloginaugt.helper.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CodeConstants {

    public static final String STRING_EMPTY = "";
    public static final String REGEX_INVALID = "Format is invalid for email";
    public static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String EMAIL_INVALID_FORMAT = "Format is invalid for email";
    public static final String AUTHENTICATED_USER = "Authenticated user successful";
    public static final String BUSINESS_ERROR = "The user with email or password does not exist";
    public static final String MANDATORY_FIELD = "The value is mandatory and not null";
    public static final String USER_NOT_EXIST = "The user not exist";
    public static final String PASSWORD_NOT_VALID = "The password is not valid";

}
