package com.project.momo.security.config;

import com.project.momo.security.filter.JwtExceptionFilter;
import com.project.momo.security.filter.JwtFilter;
import com.project.momo.security.filter.LoginAuthenticationFilter;
import com.project.momo.security.handler.AuthenticationEntryPointImpl;
import com.project.momo.security.handler.LoginAuthenticationFailureHandler;
import com.project.momo.security.handler.LoginAuthenticationSuccessHandler;
import com.project.momo.security.jwt.TokenProvider;
import com.project.momo.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final AuthorizationService authorizationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/api/signup/**", "/api/login", "/favicon/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPointImpl());

        http.addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtFilter(), LoginAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter(), JwtFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter loginFilter = new LoginAuthenticationFilter("/api/login", authenticationManager());
        loginFilter.setAuthenticationSuccessHandler(new LoginAuthenticationSuccessHandler(tokenProvider, authorizationService));
        loginFilter.setAuthenticationFailureHandler(new LoginAuthenticationFailureHandler());
        return loginFilter;
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(tokenProvider);
    }

    @Bean
    public JwtExceptionFilter jwtExceptionFilter() {
        return new JwtExceptionFilter(tokenProvider);
    }
}
