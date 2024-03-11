package com.kindergarten.kindergarten.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                // .requestMatchers(HttpMethod.POST, "*/*").permitAll()
                .requestMatchers("/accesspermission").hasAuthority("ap_perm")
                .requestMatchers("/accesspermission/add").hasAuthority("ap_add_perm")
                .requestMatchers("/accesspermission/delete/*").hasAuthority("ap_delete_perm")
                .requestMatchers("/accesspermission/edit/*").hasAuthority("ap_edit_perm")
                .requestMatchers("/compte").hasAuthority("compte_perm")
                .requestMatchers("/compte/new").hasAuthority("compte_new_perm")
                .requestMatchers("/compte/delete/*").hasAuthority("compte_delete_perm")
                .requestMatchers("/compte/setperms/*").hasAuthority("compte_setperms_perm")
                .requestMatchers("/director/kidergarten").hasAuthority("direc_kg_perm")
                .requestMatchers("/director/kidergarten/new").hasAuthority("direc_kg_new_perm")
                .requestMatchers("/director/kidergarten/edit/*").hasAuthority("direc_kg_edit_perm")
                .requestMatchers("/director/kidergarten/delete/*").hasAuthority("direc_kg_delete_perm")
                .requestMatchers("/director/profile").hasAuthority("dir_profile_perm")
                .requestMatchers("/register").permitAll()
                .requestMatchers("/error/*").permitAll()
                .requestMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/accessdenied");

        return http.build();
    }

    @Bean
    DataSource getDS() {
        DataSourceBuilder dsb = DataSourceBuilder.create();
        dsb.driverClassName("org.postgresql.Driver");
        dsb.url("jdbc:postgresql://localhost/dbkindergarten");
        dsb.username("postgres");
        dsb.password("postgres");
        return dsb.build();
    }

    @Bean
    JdbcUserDetailsManager getUserDM(DataSource ds) {
        return new JdbcUserDetailsManager(ds);
    }

    @Bean
    BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
