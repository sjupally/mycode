package com.abhaya.vehicle.tracking.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.abhaya.vehicle.tracking.services.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter 
{

	@Autowired private UserService userDetailsService;

	@Bean
    public BCryptPasswordEncoder passwordEncoder() 
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() 
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder( passwordEncoder() );
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception 
    {
        auth
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception 
    {
       /* http
        .csrf().disable()
        .requiresChannel()
        .antMatchers("/mobile/**").requiresSecure()
        .antMatchers("v1/**").requiresSecure()
        .and().
        authorizeRequests()
        .antMatchers("/mobile*")
        .permitAll();*/
    	
    	http
    	.headers().addHeaderWriter(new HeaderWriter() {
			@Override
			public void writeHeaders(HttpServletRequest arg0, HttpServletResponse arg1) {
				arg1.setHeader("X-Frame-Options", "*");
			}
		})
		.frameOptions().and()
		.httpStrictTransportSecurity().includeSubDomains(true).disable().and()
        .authorizeRequests()
        .antMatchers("v1/**").authenticated()
        .antMatchers(HttpMethod.OPTIONS).permitAll()
        .antMatchers("/mobile/**").permitAll()
        .antMatchers("/v2/**").permitAll()
        .and().httpBasic().and()
        .csrf().disable();
       
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception 
    {
        return super.authenticationManagerBean();
    }
    @Bean
    public FilterRegistrationBean<CorsFilter>  corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config); // Global for all paths
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}