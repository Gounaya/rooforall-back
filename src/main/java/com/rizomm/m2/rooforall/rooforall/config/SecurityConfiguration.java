package com.rizomm.m2.rooforall.rooforall.config;

import com.rizomm.m2.rooforall.rooforall.repositories.UserRepository;
import com.rizomm.m2.rooforall.rooforall.security.JwtAuthenticationFilter;
import com.rizomm.m2.rooforall.rooforall.security.JwtAuthorizationFilter;
import com.rizomm.m2.rooforall.rooforall.security.UserPrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserPrincipalDetailService userPrincipalDetailService;

    @Autowired
    private UserRepository userRepository;

    public SecurityConfiguration(UserPrincipalDetailService userPrincipalDetailService, UserRepository userRepository) {
        this.userPrincipalDetailService = userPrincipalDetailService;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // used in form auth
                .cors().disable() // disable cors
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no need session, because we send a token in each request
                .and()
                // add jwt filters (1. authentication, 2. authorization)
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // creating token
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository)) // decrypting token
                .authorizeRequests()
                .antMatchers("/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger.json").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/signUp").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/getInfo").authenticated()
                .antMatchers(HttpMethod.GET, "/api/users/listing").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/users/{userId}").hasRole("ADVISOR")
                .antMatchers(HttpMethod.GET, "/api/users/affectedUsers").hasRole("ADVISOR")
                .anyRequest().authenticated();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userPrincipalDetailService);

        return daoAuthenticationProvider;
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
