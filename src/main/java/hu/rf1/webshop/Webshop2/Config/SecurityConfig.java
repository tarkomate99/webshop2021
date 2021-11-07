package hu.rf1.webshop.Webshop2.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.HttpSession;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    AuthenticationSuccessWithSessionHandler successHandler = new AuthenticationSuccessWithSessionHandler();

    @Autowired
    public void configureAuth(AuthenticationManagerBuilder auth) throws Exception{
            auth
                .inMemoryAuthentication()
                    .withUser("usr")
                    .password("{noop}usr")
                    .roles("USER")
                .and()
                    .withUser("admin")
                    .password("{noop}admin")
                    .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity httpSec) throws Exception{
        httpSec
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/")
                        .permitAll()
                        .antMatchers("/delete").hasRole("ADMIN")
                        .antMatchers("/ratings/**").hasRole("ADMIN")
                        .antMatchers("/orders/**").hasRole("ADMIN")
                        .antMatchers("/users/**").hasRole("ADMIN")
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .antMatchers("/registration").permitAll()
                        .antMatchers("/reg").permitAll()
                        .and()
                 .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .successHandler(successHandler)
                    .and()
                .logout()
                    .logoutSuccessUrl("/login?logout")
                    .logoutSuccessHandler(successHandler)
                    .permitAll();
    }

}
