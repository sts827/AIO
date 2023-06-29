package com.wayplus.waytraveler.config;


import com.wayplus.waytraveler.config.handler.LoginFailureHandler;
import com.wayplus.waytraveler.config.handler.LoginSuccessHandler;
import com.wayplus.waytraveler.config.handler.SecurityAccessDeniedHandler;
import com.wayplus.waytraveler.util.CustomBcryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomBcryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                .antMatchers("/member/**").access("hasAnyRole('ADMIN', 'USER')")
                .antMatchers("/**").permitAll();
        http.formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/user/login-progress")
                .usernameParameter("user-email")
                .passwordParameter("user-pass")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler);
        http.logout()
                .logoutUrl("/user/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
        http.exceptionHandling()
                .accessDeniedHandler(new SecurityAccessDeniedHandler());
        http.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .maximumSessions(1)
                .expiredUrl("/user/session-expired");

        return http.build();
    }
}
