package com.wildcore.core.config.mail;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailConfiguration {
    private String from;
    private String fromName;
    protected RecipientConfig user;
    protected RecipientConfig admin;
}
