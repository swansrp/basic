package com.srct.service.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@Configuration
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService customUserService() { // Regist UserDetailsService bean
        return new CustomUserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(new BCryptPasswordEncoder()); // user
                                                                                                   // Details
                                                                                                   // Service验证
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http
        // .authorizeRequests(）
        // .antMatchers("/hello").hasAnyAuthority("ADMIN")
        // .anyRequest().authenticated()
        // .and()
        // .formLogin()
        // .defaultSuccessUrl("/hello")
        // .permitAll()
        // .and()
        // .logout()
        // .permitAll();
        http.csrf().disable().authorizeRequests().anyRequest().permitAll();
    }
}
