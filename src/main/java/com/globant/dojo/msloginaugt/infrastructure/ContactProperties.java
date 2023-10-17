package com.globant.dojo.msloginaugt.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "config.swagger")
@Component
@Getter
@Setter
public class ContactProperties {

	private String name;
	private String url;
	private String email;
}