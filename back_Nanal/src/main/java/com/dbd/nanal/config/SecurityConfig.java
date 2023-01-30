package com.dbd.nanal.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.dbd.nanal.config.security.JwtAuthenticationFilter;
import com.dbd.nanal.config.security.JwtTokenProvider;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .cors(withDefaults())
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)    // 세션 미사용 설정
            .and()
            .authorizeHttpRequests()
                .antMatchers(HttpMethod.GET, "/user/profile/**").hasRole("USER")
                .antMatchers("/**").permitAll()
//            .antMatchers(
//                "/user/signup",
//                "/user/login",
//                // Swagger 관련 URL
//                "/v2/api-docs/**",
//                "/swagger-resources/**",
//                "/swagger-ui/**",
//                "/webjars/**",
//                "/swagger/**",
//                "/sign-api/exception/**"
//            ).permitAll()
//            .antMatchers("/**").hasRole("USER")
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://192.168.100.94:3000",
            "http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("*"));
//        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedHeaders(Arrays.asList("Accept", "Accept-Language", "Content-Language", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
