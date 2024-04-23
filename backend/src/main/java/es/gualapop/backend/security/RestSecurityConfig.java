package es.gualapop.backend.security;

import es.gualapop.backend.security.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.SecureRandom;

@Configuration
@Order(1)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(10, new SecureRandom());
//    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/api/**");

            // URLs that need authentication to access to it
            http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/users/**").hasRole("USER");
            http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN");

            http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/products/**").hasRole("USER");
            http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/products/**").hasRole("USER");
            http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/products/**").hasAnyRole("ADMIN", "USER");

            http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/reviews/**").hasRole("ADMIN");
            http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/reviews/**").hasAnyRole("USER", "ADMIN");

            http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/reports/**").hasRole("ADMIN");
            http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/reports/").hasAnyRole("USER", "ADMIN");

            // Other URLs can be accessed without authentication
            http.authorizeRequests().anyRequest().permitAll();

            // Disable CSRF protection (it is difficult to implement in REST APIs)
            http.csrf().disable();

            // Disable Http Basic Authentication
            http.httpBasic().disable();

            // Disable Form login Authentication
            http.formLogin().disable();

            // Avoid creating session
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            // Add JWT Token filter
            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


    }
}
