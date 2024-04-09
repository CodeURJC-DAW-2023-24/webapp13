package es.gualapop.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private RepositoryUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Public pages
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/loginerror").permitAll();
        http.authorizeRequests().antMatchers("/logout").permitAll();
        http.authorizeRequests().antMatchers("/search").permitAll();
        http.authorizeRequests().antMatchers("/product/category/**").permitAll();


        // Private pages (all other pages)
        http.authorizeRequests().antMatchers("/admin").hasAnyRole( "ADMIN");
        http.authorizeRequests().antMatchers("/newProduct").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/profile").hasAnyRole("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/reportPanel").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/checkout/**").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/deleteUser/**").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/deleteAccount/**").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("product/**/delete").hasAnyRole("ADMIN");

        //API secutrity
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                .anyRequest().authenticated().and().httpBasic();
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/products/**").hasRole("USER")
                .anyRequest().authenticated().and().httpBasic();



        // Login form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/profile");
        http.formLogin().failureUrl("/loginerror");

        // Logout
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");

        // Disable CSRF at the moment
        http.csrf().disable();
    }
}
