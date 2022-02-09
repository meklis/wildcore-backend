package com.wildcore.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "beta_requests")
@Getter
@Setter
@ToString
public class BetaRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("created_at")
    @CreationTimestamp
    private Date createdAt;
    private String name;
    private String email;
    private String company;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String message;
    @JsonProperty("sent_at")
    private Date sentAt;
    private boolean sent;
}
