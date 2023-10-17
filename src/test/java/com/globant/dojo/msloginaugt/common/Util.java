package com.globant.dojo.msloginaugt.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Util {

    public static final String LOGIN_PATH = "/api/v1/login";
    public static final String LOGIN_DTO = "contracts/LoginDTO";
    public static final String USER_DTO = "contracts/UserDto";
    public static final String LOGIN_DTO_BAD = "contracts/LoginDTO_bad";
    public static final String EXCEPTION_MESSAGE = "Instance of postgres not run";


    public static String readFile(final String fileName) throws IOException {
        byte[] file = Files.readAllBytes(Paths.get(String.format("./src/test/resources/%s.json", fileName)));
        return new String(file);
    }
}
