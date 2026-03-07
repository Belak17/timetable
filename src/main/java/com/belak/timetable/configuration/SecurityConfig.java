package com.belak.timetable.configuration;


import com.belak.timetable.component.CustomAuthSuccessHandler;
import com.belak.timetable.component.CustomAuthenticationFailureHandler;
import com.belak.timetable.user.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private CustomAuthSuccessHandler customAuthSuccessHandler;
    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
    }
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler ;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/forgot", "/css/**",
                                "/js/**" , "/images/**" , "/icons/**").permitAll()
                        .requestMatchers("/admin/**","/admin").hasRole("ADMIN")
                        .requestMatchers("/professor/**").hasRole("PROFESSOR")
                        .requestMatchers("/student/**").hasRole("STUDENT")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                       .loginPage("/login")
                        .usernameParameter("userId")  // correspond à ton champ username
                        .passwordParameter("password")
                        .successHandler(customAuthSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler)// ici tu rediriges selon le rôle
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                )


        ;
        return http.build();
}
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        // Spring Security récupère automatiquement le UserDetailsService injecté
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


}
