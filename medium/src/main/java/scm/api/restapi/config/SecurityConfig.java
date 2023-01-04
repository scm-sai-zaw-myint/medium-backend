package scm.api.restapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import scm.api.restapi.medium.common.PropertyUtil;
import scm.api.restapi.medium.jwt.JWTFilterChain;
import scm.api.restapi.medium.persistence.repo.UsersRepo;

@SuppressWarnings("deprecation")
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = false, securedEnabled = false, jsr250Enabled = true
    )
public class SecurityConfig {
    
    @Autowired
    private UsersRepo usersRepo;
    
    @Autowired
    private JWTFilterChain jwtFilterChain;
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return usersRepo.findByEmail(email).orElseThrow(
                        () -> new UsernameNotFoundException("Email " + email + " not found!")
                );
            }
        };
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.authorizeHttpRequests()
        .requestMatchers("/api/auth/**","/api/posts","/api/posts/**","/api/assets/image/**","/api/assets/profile/**").permitAll()
        .anyRequest().authenticated();
        
        http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String eJson = PropertyUtil.eToJson(ex, HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(eJson);
        });

        http.addFilterBefore(jwtFilterChain, UsernamePasswordAuthenticationFilter.class);
        http.cors();
        return http.build();
    }
}
