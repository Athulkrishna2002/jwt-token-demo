package com.trading.tradingApplication.model.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trading.tradingApplication.domain.USER_ROLE;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user")  // This explicitly maps the entity to the "user" table
public class UserDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser")  // Match the column name in the database
    private Long id;

    @Column(name = "name")  // Map to "name" column
    private String fullName;

    @Column(name = "email")  // Map to "email" column
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // Prevent password from being exposed
    @Column(name = "password")  // Map to "password" column
    private String password;

    @Embedded
    private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();

    @Enumerated(EnumType.STRING)
    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;
}
