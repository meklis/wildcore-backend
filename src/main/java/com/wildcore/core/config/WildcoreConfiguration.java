package com.wildcore.core.config;

import com.wildcore.core.config.mail.MailConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "wildcore")
@Getter
@Setter
public class WildcoreConfiguration {
    private MailConfiguration mail;
}

