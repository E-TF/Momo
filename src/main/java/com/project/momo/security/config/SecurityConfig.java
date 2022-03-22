package com.project.momo.security.config;

import com.project.momo.security.filter.JwtExceptionFilter;
import com.project.momo.security.filter.JwtFilter;
import com.project.momo.security.filter.LoginAuthenticationFilter;
import com.project.momo.security.jwt.AuthenticationEntryPointImpl;
import com.project.momo.security.jwt.LoginAuthenticationFailureHandler;
import com.project.momo.security.jwt.LoginAuthenticationSuccessHandler;
import com.project.momo.security.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;
    private final LoginAuthenticationFilter loginAuthenticationFilter;

    private final LoginAuthenticationSuccessHandler successHandler;
    private final LoginAuthenticationFailureHandler failureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers(GET, "/favicon/**").permitAll()
                .mvcMatchers(POST, "/api/login").permitAll()
                .mvcMatchers(POST, "/api/s3/member/profile").hasAnyRole("TEMPORARY", "USER")
                .mvcMatchers(POST, "/api/s3/club/profile").hasRole("USER")
                .mvcMatchers(POST, "/api/members/signup").permitAll()
                .mvcMatchers(POST, "/api/members/signup/oauth").hasRole(Role.TEMPORARY)
                .mvcMatchers(GET, "/api/members/loginid/duplicate").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        http.oauth2Login()
                .userInfoEndpoint()
                .and()
                .successHandler(successHandler).failureHandler(failureHandler);

        http.addFilterAt(loginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtFilter, LoginAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter, JwtFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
