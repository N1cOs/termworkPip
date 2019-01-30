package ru.ifmo.se.termwork.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import ru.ifmo.se.termwork.security.Authorities;
import ru.ifmo.se.termwork.security.JwtAuthenticationFilter;
import ru.ifmo.se.termwork.security.JwtAuthenticationProvider;
import ru.ifmo.se.termwork.security.NoRedirectStrategy;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "ru.ifmo.se.termwork.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String PUBLIC_URLS = "/api/public/**";

    private final static String STUDENT_URLS = "/api/student/**";

    private final static String WORKER_URLS = "/api/worker/**";

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authenticationProvider(jwtAuthenticationProvider)
                .addFilterBefore(restFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers(STUDENT_URLS).hasAuthority(Authorities.STUDENT)
                    .antMatchers(WORKER_URLS).hasAuthority(Authorities.WORKER)
                    .and()
                .formLogin().disable()
                .logout().disable()
                .csrf().disable()
                .httpBasic().disable();
    }

    @Bean
    JwtAuthenticationFilter restFilter() throws Exception {
        NegatedRequestMatcher protectedUrls =
                new NegatedRequestMatcher(new AntPathRequestMatcher(PUBLIC_URLS));
        JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(protectedUrls);
        authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setAuthenticationSuccessHandler(successHandler());
        return authenticationFilter;
    }

    @Bean
    SimpleUrlAuthenticationSuccessHandler successHandler(){
        SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setRedirectStrategy(new NoRedirectStrategy());
        return successHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
