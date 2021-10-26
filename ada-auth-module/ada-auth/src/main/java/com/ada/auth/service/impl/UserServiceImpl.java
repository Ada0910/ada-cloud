package com.ada.auth.service.impl;

import com.ada.auth.constant.MessageConstant;
import com.ada.auth.dto.SecurityUserDto;
import com.ada.auth.dto.UserDto;
import com.ada.auth.service.UserService;
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

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//根据用户名查找用户,用户实体类
		UserDto userDto = userService.loadUserByUsername(username);

		//获取权限（测试代码用的代码）
		GrantedAuthority authority = new SimpleGrantedAuthority("admin");
		List<GrantedAuthority> authorities = new ArrayList<>(1);
		authorities.add(authority);

		//登录用户对象
		SecurityUserDto securityUserDto = new SecurityUserDto(userDto);

		/**
		 * 校验用户的状态
		 */
		if (!securityUserDto.isEnabled()) {
			//账号被禁用
			throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
		} else if (!securityUserDto.isAccountNonLocked()) {
			//账号被锁定
			throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
		} else if (!securityUserDto.isAccountNonExpired()) {
			//账号过期
			throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
		} else if (!securityUserDto.isCredentialsNonExpired()) {
			//账户的登录凭证已过期
			throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
		}
		return securityUserDto;
	}
}
