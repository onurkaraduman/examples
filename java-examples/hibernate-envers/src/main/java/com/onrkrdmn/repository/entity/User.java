package com.onrkrdmn.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Audited
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String firstname;
    private String lastname;
    private Date birthday;

    @OneToOne(cascade = CascadeType.ALL)
    private Session session;
}
