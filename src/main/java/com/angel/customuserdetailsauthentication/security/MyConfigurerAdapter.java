package com.angel.customuserdetailsauthentication.security;

import com.angel.customuserdetailsauthentication.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class MyConfigurerAdapter  extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

//    Because we are using H2 DB, in order to access the console which is available through devtools dependency
//    , we have to disable the frameOptions as below
//    httpBasic() enables us to authenticate through Http Basic Authentication method
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/confidential").hasRole("ADMIN")
                .antMatchers("/public").hasRole("USER")
                .antMatchers("/**").permitAll()
                .and()
                .csrf().disable().headers().frameOptions().disable()
                .and()
                .httpBasic();
    }
}
