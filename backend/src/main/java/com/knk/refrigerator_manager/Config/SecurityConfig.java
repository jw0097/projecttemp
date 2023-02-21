package com.knk.refrigerator_manager.Config;

import com.knk.refrigerator_manager.user.CustomUserDetailsService;
import com.knk.refrigerator_manager.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    @Autowired
    private final UserService userService;

//    AuthenticationManager authenticationManager;
//
    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(customUserDetailsService);
//        authenticationManager = authenticationManagerBuilder.build();
        http.csrf().disable();
        http.cors().disable();
        http.headers().frameOptions().disable();
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/registerUser", "/*").permitAll()
                .requestMatchers("/user/user").hasRole("USER")
                .requestMatchers("/admin/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
        );
        http.formLogin().loginProcessingUrl("/login")
                .permitAll();
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, CustomUserDetailsService customUserDetailsService) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder()).and().build();
    }
//    @Bean
//    public AuthenticationManager configure(AuthenticationManagerBuilder auth) throws Exception {
//        return auth
//                .userDetailsService(customUserDetailsService)
//                .passwordEncoder(getPasswordEncoder()).and().build();
//    }
    //    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http.authorizeExchange()
//                .pathMatchers("/registerUser").permitAll()
//                .anyExchange().authenticated()
//                .and()
//                .formLogin().loginPage("/login").and()
//                .logout().logoutUrl("/logout").and()
//                .csrf().disable();
//
////        http.authenticationManager(authenticationManager());
////        http.securityContextRepository(securityContextRepository());
//
//        return http.build();
//    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .headers().frameOptions().disable() //2
//                .and()
//                .authorizeHttpRequests() //3
//                .requestMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll()
//                .requestMatchers("/api/v1/**").permitAll()//4
//                .anyRequest().authenticated()//5
//                .and()
//                .logout()
//                .logoutSuccessUrl("/"); //6
//        return http.build();
//    }
}