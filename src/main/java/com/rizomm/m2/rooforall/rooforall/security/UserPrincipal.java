package com.rizomm.m2.rooforall.rooforall.security;

import com.rizomm.m2.rooforall.rooforall.entites.User;
import com.rizomm.m2.rooforall.rooforall.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Builder
public class UserPrincipal implements UserDetails {

    private User user;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Extract list of roles and concat "ROLE_" to each role
        this.user.getRoles().forEach(role -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName());
            authorities.add(authority);
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isActive();
    }
}
