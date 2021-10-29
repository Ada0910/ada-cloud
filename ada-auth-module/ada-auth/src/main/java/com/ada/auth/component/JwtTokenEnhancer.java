package com.ada.auth.component;

import com.ada.auth.dto.SecurityUserDto;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: JWT 内容增强
 *
 * @ClassName : JwtTokenEnhancer
 * @date :2021/10/29  23:17
 * @author : Ada
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
		SecurityUserDto securityUser = (SecurityUserDto) oAuth2Authentication.getPrincipal();
		Map<String, Object> info = new HashMap<>();
		//把用户ID设置到JWT中
		info.put("id", securityUser.getId());
		info.put("client_id", securityUser.getClientId());
		((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);
		return oAuth2AccessToken;
	}
}
