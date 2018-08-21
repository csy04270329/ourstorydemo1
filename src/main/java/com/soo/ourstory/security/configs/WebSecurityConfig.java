package com.soo.ourstory.security.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsService userDetailsService;


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.httpBasic();

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/accounts/**")
                    .hasRole("ADMIN")
                .antMatchers("/accounts")
                    .hasRole("USER")
                .anyRequest()
                    .permitAll()
                .and()
                .formLogin()
                    .loginPage("/accounts/login")
                    .permitAll()
                .defaultSuccessUrl("/")
                .and()
                .logout();

    }
}
