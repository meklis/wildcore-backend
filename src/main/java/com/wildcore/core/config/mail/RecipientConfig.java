package com.wildcore.core.config.mail;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipientConfig {
    private Boolean sent;
    private String template;
    private List<String> addresses;
}
