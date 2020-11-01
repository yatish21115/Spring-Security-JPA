package com.login.springsecurityjpa.Models;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@NotNull(message = "Username is mandatory")
    private String username;

    //@Length(min = 5, message = "Password must be at least 5 characters")
    //@NotNull(message = "Password is mandatory")
    private String password;

    private boolean enabled;

    //@NotNull(message = "First Name is mandatory")
    private String firstname;

    //@NotNull(message = "Last Name is mandatory")
    private String lastname;

    //@Email(message = "Email is not valid")
    //@NotNull(message = "Email is mandatory")
    private String email;

    private String security;

    private String answer;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
