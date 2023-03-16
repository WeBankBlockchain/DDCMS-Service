package com.webank.databrain.config;

import com.webank.databrain.filter.JwtAuthenticationFilter;
import com.webank.databrain.handler.AccessDeniedHandlerHandler;
import com.webank.databrain.handler.AuthenticationEntryPointHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationEntryPointHandler authenticationEntryPointHandler;

    @Autowired
    private AccessDeniedHandlerHandler accessDeniedHandlerHandler;

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
                .antMatchers("/api/account/register").permitAll()  // 直接放行
                .antMatchers("/api/account/pageQueryCompany").permitAll()
                .antMatchers("/api/account/queryCompanyByUsername").permitAll()
                .antMatchers("/api/account/queryPersonByUsername").permitAll()
                .antMatchers("/api/account/getHotCompanies").permitAll()
                .antMatchers("/api/account/searchCompany").permitAll()
                .antMatchers("/api/account/searchPerson").permitAll()
                .antMatchers("/api/schema/pageQuerySchema").permitAll()
                .antMatchers("/api/schema/querySchemaById").permitAll()
                .antMatchers("/api/file/download").permitAll()
                .antMatchers("/api/file/upload").permitAll()
                .antMatchers("/api/product/getHotProducts").permitAll()
                .antMatchers("/api/product/pageQueryProduct").permitAll()
                .antMatchers("/api/product/queryProductById").permitAll()
                .antMatchers("/api/tag/getHotTags").permitAll()
                .antMatchers("/api/account/login").permitAll()  //允许匿名访问
//                .antMatchers("/account/login").hasAnyAuthority("Admin", "Person")
//                .anyRequest().authenticated()
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