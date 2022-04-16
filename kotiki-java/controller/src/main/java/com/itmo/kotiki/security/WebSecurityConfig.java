package com.itmo.kotiki.security;

import com.itmo.kotiki.service.PersonService;
import com.itmo.kotiki.service.implementation.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PersonService personService;

    @SuppressWarnings("deprecation")
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        var passwordEncoder = NoOpPasswordEncoder.getInstance();
        auth.userDetailsService(personService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/person/**").hasRole("ADMIN")
                    .antMatchers("/**").hasAnyRole("ADMIN", "USER")
                .and().formLogin()
                .and().logout()
                .and().httpBasic()
                .and().csrf().disable();
    }
}
