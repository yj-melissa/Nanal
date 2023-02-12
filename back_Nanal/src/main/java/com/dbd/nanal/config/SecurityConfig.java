package com.dbd.nanal.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.dbd.nanal.config.oauth.OAuthFailureHandler;
import com.dbd.nanal.config.oauth.OAuthSuccessHandler;
import com.dbd.nanal.config.security.CustomAuthenticationEntryPoint;
import com.dbd.nanal.config.security.CustomLogoutSuccessHandler;
import com.dbd.nanal.config.security.JwtAuthenticationFilter;
import com.dbd.nanal.config.security.JwtTokenProvider;
import com.dbd.nanal.config.oauth.CustomOAuthUserService;
import com.dbd.nanal.config.security.TokenAccessDeniedHandler;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private CustomOAuthUserService customOAuthUserService;
    @Autowired private OAuthSuccessHandler oAuthSuccessHandler;
    @Autowired private OAuthFailureHandler oAuthFailureHandler;
    @Autowired private TokenAccessDeniedHandler tokenAccessDeniedHandler;
    @Autowired private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired private CustomLogoutSuccessHandler customLogoutSuccessHandler;


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
//                .antMatchers(
//                    "/user/profile",
//                    "/user/test",
//                    "/group/**").hasRole("USER")
//                .antMatchers("/**").permitAll()
                .antMatchers(
                    "/",
                    "/user/signup",
                    "/user/login",
                    "/user/oauth2",
                    "/oauth2/**",
                    "/user/refresh",    // accessToken 재발급
                    "/user/redirectTest",
                    "/user/check/**",
                    "/user/validate/**",
                    // Swagger 관련 URL
                    "/v2/api-docs/**",
                    "/swagger-resources/**",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/swagger/**",
                    "/sign-api/exception/**"
                ).permitAll()
            .antMatchers("/**").hasRole("USER")
            .and()
            .logout()
            .logoutUrl("/user/logout")
            .logoutSuccessUrl("/")
            .deleteCookies("JSSESSIONID", "refreshToken")
            .logoutSuccessHandler(customLogoutSuccessHandler)
            .and()
            .exceptionHandling()
            .accessDeniedHandler(tokenAccessDeniedHandler)
            .authenticationEntryPoint(customAuthenticationEntryPoint)
            .and()
            .oauth2Login()
            .redirectionEndpoint()     // 콜백 요청 후 리다이렉트할 url
            .and()
            .authorizationEndpoint()
            .baseUri("/user/oauth2")
            .and()
            .userInfoEndpoint()
                .userService(customOAuthUserService)
            .and()
            .successHandler(oAuthSuccessHandler)
            .failureHandler(oAuthFailureHandler);

        http
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
            UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://192.168.100.94:3000",
            "http://192.168.100.204:3000",
            "http://192.168.100.206:3000",
            "http://192.168.0.25:3000",
            "http://192.168.0.23:3000",
            "http://192.168.35.39:3000",
            "http://172.30.1.43:3000",
            "http://192.168.0.164:3000",
            "http://14.46.142.235:3000",
            "http://192.168.45.214:3000",
            "http://192.168.0.9:3000",
            "http://localhost:3000",
            "https://i8d110.p.ssafy.io",
            "https://3.36.51.212"));
        configuration.setAllowedMethods(Arrays.asList("*"));
//        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedHeaders(Arrays.asList("Accept", "Accept-Language", "Authorization", "Content-Language", "Content-Type"));
        configuration.setAllowCredentials(true);
//        configuration.addExposedHeader("*");
        configuration.addExposedHeader("accessToken");
        configuration.addExposedHeader("accesstoken");
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

