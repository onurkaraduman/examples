package com.onrkrdmn.springbootcertauth.restapi.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * All requests come here first and authentication is done
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Log
public class X509AuthenticationAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private MockUserStore mockUserStore;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // since it is not a webpage, no need cross site request forgery
        http.csrf().disable();


        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                // disable session creation means we will receive certificate for each request
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().x509().subjectPrincipalRegex("CN=(.*?)(?:,|$)").userDetailsService(userDetailsService());
    }


    @Override
    protected UserDetailsService userDetailsService() {
        return subject -> {
            String user = mockUserStore.getUser(subject);
            if (user != null) {
                return new User(user, "", AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
            }
            throw new UsernameNotFoundException("User couldn't be found");
        };
    }
}
