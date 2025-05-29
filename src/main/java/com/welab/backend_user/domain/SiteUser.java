package com.welab.backend_user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "site_user")
public class SiteUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    @Getter @Setter
    private String userId;

    @Column(name = "password", nullable = false)
    @Getter @Setter
    private String password;

    @Column(name = "phone_number", nullable = false)
    @Getter @Setter
    private String phoneNumber;

    @Column(name = "deleted", nullable = false)
    @Getter @Setter
    private Boolean deleted = false;

//    @Column(name = "age")
//    @Getter @Setter
//    private Integer age;

}
