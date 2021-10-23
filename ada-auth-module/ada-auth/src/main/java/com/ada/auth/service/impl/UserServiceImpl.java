package com.ada.auth.service.impl;

import com.ada.auth.constant.MessageConstant;
import com.ada.auth.dto.SecurityUserDto;
import com.ada.auth.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 获取用户校验的业务逻辑层
 *
 * @ClassName : UserServiceImpl
 * @date :2021/10/8  23:24
 * @author : Ada
 */
@Service
public class UserServiceImpl  implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//根据用户名查找用户,用户实体类
		UserDto userDto = new UserDto();


		GrantedAuthority authority = new SimpleGrantedAuthority("admin");
		List<GrantedAuthority> authorities = new ArrayList<>(1);
		authorities.add(authority);

		SecurityUserDto securityUserDto = new SecurityUserDto(userDto);

		if (!securityUserDto.isEnabled()) {
			throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
		} else if (!securityUserDto.isAccountNonLocked()) {
			throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
		} else if (!securityUserDto.isAccountNonExpired()) {
			throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
		} else if (!securityUserDto.isCredentialsNonExpired()) {
			throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
		}
		// 返回用户信息，注意加密
		return securityUserDto;
	}
}
