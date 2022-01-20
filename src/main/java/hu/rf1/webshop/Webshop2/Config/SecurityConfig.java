package hu.rf1.webshop.Webshop2.Config;

import hu.rf1.webshop.Webshop2.Model.User;
import hu.rf1.webshop.Webshop2.Repository.RoleRepository;
import hu.rf1.webshop.Webshop2.Repository.UserRepository;
import hu.rf1.webshop.Webshop2.Service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpSession;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    AuthenticationSuccessWithSessionHandler successHandler = new AuthenticationSuccessWithSessionHandler();

    private User user;

    private RoleRepository roleRepository;

    @Autowired
    public SecurityConfig(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Autowired
    private UserDetailsService userService;


    @Autowired
    public void configureAuth(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity httpSec) throws Exception{
        httpSec
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/").permitAll()
                           .antMatchers("/prod-info/**").permitAll()
//                            .antMatchers("/ratings/**").permitAll()
//                            .antMatchers("/orders/**").permitAll()
//                            .antMatchers("/users/**").permitAll()
//                            .antMatchers("/admin/**").permitAll()
//                            .antMatchers("/registration").permitAll()
//                            .antMatchers("/reg").permitAll()
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
