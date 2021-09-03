package store.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import store.services.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests()

                .antMatchers("/").permitAll()
                .antMatchers("/js/app.js").permitAll()
                .antMatchers("/css/main.css").permitAll()

                .antMatchers("/register-user").permitAll()

                .antMatchers("/goods/get-list").permitAll()

                .antMatchers("/get-current-user").permitAll()
                .antMatchers("/goods/add").hasRole("ADMIN")
                .antMatchers("/goods/delete").hasRole("ADMIN")
                .antMatchers("/goods/update").hasRole("ADMIN")

                // For stress test purpose
                .antMatchers("/purchase/makeOne").permitAll()

                .anyRequest().authenticated();

        // Настройка для входа в систему
        http.formLogin((customize) -> {
            customize.loginPage("/");
            customize.loginProcessingUrl("/authorize");
            customize.successHandler(authenticationSuccessHandler());
            customize.failureHandler(authenticationFailureHandler());
        });

        // Настройка выхода из системы
        http.logout((customize) -> {
            customize.logoutSuccessUrl("/");
        });
    }

    @Autowired
    protected void configureGlobal(
            AuthenticationManagerBuilder auth,
            UserService userService
    ) throws Exception {

        auth.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder());
    }
}