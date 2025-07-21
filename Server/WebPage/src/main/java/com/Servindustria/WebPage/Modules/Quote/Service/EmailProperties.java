package com.Servindustria.WebPage.Modules.Quote.Service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailProperties {
    private String username;
    private String password;
}
