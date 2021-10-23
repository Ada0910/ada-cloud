package com.ada.auth.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description: 获取用户校验的业务逻辑层
 *
 * @ClassName : UserServiceImpl
 * @date :2021/10/8  23:24
 * @author : Ada
 */
@Service
public class UserServiceImpl  implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		return null;
	}
}
