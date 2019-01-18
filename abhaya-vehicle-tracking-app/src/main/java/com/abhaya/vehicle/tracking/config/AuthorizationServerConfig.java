package com.abhaya.vehicle.tracking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import io.jsonwebtoken.SignatureAlgorithm;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter 
{
	
	private int accessTokenValiditySeconds = 10000;
    private int refreshTokenValiditySeconds = 30000;

    private static final String resourceId = "abhaya";

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired BCryptPasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(this.authenticationManager)
                .tokenServices(tokenServices())
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
        .tokenKeyAccess("hasAuthority('SUPER_ADMIN') || hasAuthority('ROLE_ADMIN') || hasAuthority('ROLE_USER')")
        .checkTokenAccess("hasAuthority('SUPER_ADMIN') || hasAuthority('ROLE_ADMIN') || hasAuthority('ROLE_USER')");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception 
    {
        clients.inMemory()
        .withClient("abhaya-app")
        .authorizedGrantTypes("client_credentials", "password", "refresh_token")
        .authorities("SUPER_ADMIN","ROLE_ADMIN","ROLE_USER")
        .scopes("read", "write")
        .resourceIds(resourceId)
        .accessTokenValiditySeconds(accessTokenValiditySeconds)
        .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
        .secret(passwordEncoder.encode("abhaya-secret"));
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }


    @Bean
    public JwtAccessTokenConverter accessTokenConverter() 
    {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        try 
        {
            converter.setSigningKey(SignatureAlgorithm.HS512.toString());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return converter;
    }
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() 
    {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        return defaultTokenServices;
    }
  }