package com.webank.databrain.config;

import com.webank.databrain.enums.AccountType;
import com.webank.databrain.filter.JwtAuthenticationFilter;
import com.webank.databrain.handler.AccessDeniedHandlerHandler;
import com.webank.databrain.handler.AuthenticationEntryPointHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationEntryPointHandler authenticationEntryPointHandler;

    @Autowired
    private AccessDeniedHandlerHandler accessDeniedHandlerHandler;

    @Autowired
    private AuthConfig authConfig;

    // encoder 加密 会自动带上salt
    // decoder 解密 return true or false
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(authConfig.getPermitAllApiList().toArray(new String[0])).permitAll()
                .antMatchers(authConfig.getAnonymousApi()).anonymous()
                .antMatchers(authConfig.getRoleAuth().getAdminAuth().toArray(new String[0])).hasAnyAuthority(AccountType.ADMIN.getRoleName())
                .antMatchers(authConfig.getRoleAuth().getCompanyAuth().toArray(new String[0])).hasAnyAuthority(AccountType.COMPANY.getRoleName())
                // .antMatchers(authConfig.getRoleAuth().getWitnessAuth().stream().toArray(String[]::new)).hasAnyAuthority(AccountType.WITNESS.getRoleName())
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter ,UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointHandler)  // 认证失败异常处理配置
                .accessDeniedHandler(accessDeniedHandlerHandler);  // 授权失败异常处理配置
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}