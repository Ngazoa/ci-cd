package org.akouma.stock.config;


import org.akouma.stock.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity

@EnableGlobalMethodSecurity(securedEnabled = true)
public class Security extends WebSecurityConfigurerAdapter {


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests().antMatchers(
                "/login","pdf-generated",
                "/logout",
                "/resources/**", "sweetalert2/**", "/css/**",
                "/css/*", "/static/**" , "jquery/*", "/sweetalert2themebootstrap-4/**",
                "/js/*", "/img/*","/fonts/*", "/img/avatars/*", "/img/icons/*",
                "/photos/*", "/upload/**", "sweetalert2/*", "/upload/avatar/clients/*",
                "/upload/avatar/*", "/upload/fichiers_transactions/*"
        ).permitAll();

        http.authorizeRequests()

        ;
        // Config Remember Me.
//        http.authorizeRequests()
//            .and() //
//            .rememberMe().tokenRepository(this.persistentTokenRepository()) //
//            .tokenValiditySeconds(365 * 24 * 60 * 60); // 24h

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider());
    }


}
