package com.url.shortner.security;


import java.util.List;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.url.shortner.security.jwt.JwtAuthenticationFilter;
import com.url.shortner.service.UserDetailsServiceImpl;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class WebSecurityConfig {
   @Autowired
	private UserDetailsServiceImpl userDetailsService;

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
    	return new JwtAuthenticationFilter();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder(); 
    }

    @Bean
    AuthenticationManager authenticateManager(AuthenticationConfiguration authenticationConfiguration)throws Exception {
    	return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider authProvider =new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

     @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173")); // Your frontend URL
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    	 http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/urls/**").authenticated()
            .requestMatchers("/{shortUrl}").permitAll()
            // Add this line to allow OPTIONS requests for all paths
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .anyRequest().authenticated()
        );

    http.authenticationProvider(authenticationProvider());
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
    }


    
}
