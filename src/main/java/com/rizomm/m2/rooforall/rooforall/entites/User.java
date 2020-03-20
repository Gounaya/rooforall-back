package com.rizomm.m2.rooforall.rooforall.entites;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = -1121865580712611227L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private boolean active;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @ManyToOne
    private User supervisor;

}
