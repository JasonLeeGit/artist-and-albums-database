package com.ltd.coders.software.artist.and.albums.database.helper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application")
public class ConfigPropertiesHelper {
	private String musicPath;
}
