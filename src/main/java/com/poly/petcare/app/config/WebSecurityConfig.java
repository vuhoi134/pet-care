package com.poly.petcare.app.config;

import com.poly.petcare.app.filter.JwtAuthenticationFilter;
import com.poly.petcare.domain.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
         return new BCryptPasswordEncoder();

        // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
//        return NoOpPasswordEncoder.getInstance();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager bean (Nhận bean trình quản lý xác thực)
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/v1/login/authenticate").permitAll() // Request dạng POST tới "/login" luôn được phép truy cập
                .antMatchers(HttpMethod.POST,"/v1/login/login").permitAll()
                .antMatchers(HttpMethod.GET,"/v1/products/**").permitAll()
                .antMatchers(HttpMethod.GET,"/v1/brand/**").permitAll()
                .antMatchers(HttpMethod.GET,"/v1/category/**").permitAll()
                .antMatchers("/v1/cart-product/**").permitAll()
                .antMatchers("/v1/user/**").permitAll()
                .antMatchers("/v1/profile/**").permitAll()
                .antMatchers(HttpMethod.GET,"/v1/supplier/**").permitAll()
                .antMatchers(HttpMethod.POST,"/v1/supplier/**").hasAnyRole("ADMIN","ADMIN_WAREHOUSE")
                .antMatchers(HttpMethod.PUT,"/v1/supplier/**").hasAnyRole("ADMIN","ADMIN_WAREHOUSE")
                .antMatchers(HttpMethod.DELETE,"/v1/supplier/**").hasAnyRole("ADMIN","ADMIN_WAREHOUSE")
                .antMatchers(HttpMethod.GET,"/v1/warehouse/**").permitAll()
                .antMatchers(HttpMethod.POST,"/v1/warehouse/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/v1/warehouse/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/v1/warehouse/**").hasRole("ADMIN")
                .antMatchers("/v1/order/**").permitAll()
                .antMatchers("/v1/profile/**").permitAll()
                .antMatchers("/admin/input/**").hasAnyRole("ADMIN","ADMIN_WAREHOUSE")
                .antMatchers("/admin/output/**").hasAnyRole("ADMIN","ADMIN_WAREHOUSE")
                .anyRequest().authenticated() // tất cả các request còn lại phải đc xác thực
                .and()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.addAllowedOrigin("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
