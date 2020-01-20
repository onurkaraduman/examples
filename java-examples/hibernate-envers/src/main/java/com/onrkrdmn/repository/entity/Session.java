package com.onrkrdmn.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Audited
@Getter
@Setter
public class Session {
    @Id
    @GeneratedValue
    private Long id;

    private String sessionId;
    private String sessionName;
}
