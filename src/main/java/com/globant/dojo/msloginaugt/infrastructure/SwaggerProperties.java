package com.globant.dojo.msloginaugt.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "config.swagger")
@Component
@Getter
@Setter
public class SwaggerProperties {

	private String title;
	private String description;
	private String terms;
	private ContactProperties contact;
	private String licence;
	private String licenceUrl;
	private String version;
}