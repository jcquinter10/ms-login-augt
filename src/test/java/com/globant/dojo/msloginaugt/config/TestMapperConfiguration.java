package com.globant.dojo.msloginaugt.config;

import com.globant.dojo.msloginaugt.model.mapper.RolMapperImpl;
import com.globant.dojo.msloginaugt.model.mapper.UserMapperImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ UserMapperImpl.class, RolMapperImpl.class})
public class TestMapperConfiguration {

}
