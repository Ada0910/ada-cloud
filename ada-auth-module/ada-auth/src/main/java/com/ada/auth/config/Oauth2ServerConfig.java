package com.ada.auth.config;

import com.ada.auth.component.JwtTokenEnhancer;
import com.ada.auth.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 认证服务器配置
 *
 * @EnableAuthorizationServer 开启认证授权中心
 * @Configuration 配置授权中心信息
 * @AllArgsConstructor 注解在类上，为类提供一个全参的构造方法
 *
 *
 * @ClassName : Oauth2ServerConfig
 * @date :2021/10/26  23:26
 * @author : Ada
 */
@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final UserServiceImpl userService;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenEnhancer jwtTokenEnhancer;


	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//允许表单认证
		security.allowFormAuthenticationForClients();
	}

	/**
	 * 添加商户信息 client模式
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("admin-app")
				.secret(passwordEncoder.encode("123456"))
				.scopes("all")
				.authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(3600 * 24)
				.refreshTokenValiditySeconds(3600 * 24 * 7)
				.and()
				.withClient("portal-app")
				.secret(passwordEncoder.encode("123456"))
				.scopes("all")
				.authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(3600 * 24)
				.refreshTokenValiditySeconds(3600 * 24 * 7);
	}

	/**
	 * 设置token的类型
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		List<TokenEnhancer> delegates = new ArrayList<>();
		delegates.add(jwtTokenEnhancer);
		delegates.add(accessTokenConverter());

		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		//配置JWT的内容增强器
		enhancerChain.setTokenEnhancers(delegates);
		endpoints.authenticationManager(authenticationManager).userDetailsService(userService).accessTokenConverter(accessTokenConverter()).tokenEnhancer(enhancerChain);
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setKeyPair(keyPair());
		return jwtAccessTokenConverter;
	}

	@Bean
	public KeyPair keyPair() {
		//从classpath下的证书中获取秘钥对
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
		return keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
	}
}
