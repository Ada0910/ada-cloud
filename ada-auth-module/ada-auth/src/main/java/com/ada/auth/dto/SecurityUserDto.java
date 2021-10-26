package com.ada.auth.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Description: 登录用户对象
 *
 * @ClassName : SecurityUserDto
 * @date :2021/10/23  21:09
 * @author : Ada
 */
@Data
public class SecurityUserDto  implements UserDetails {
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 用户状态
	 */
	private Boolean enabled;
	/**
	 * 登录客户端ID
	 */
	private String clientId;
	/**
	 * 权限数据
	 */
	private Collection<SimpleGrantedAuthority> authorities;

	public SecurityUserDto(UserDto userDto) {
		this.setId(userDto.getId());
		this.setUsername(userDto.getUsername());
		this.setPassword(userDto.getPassword());
		this.setEnabled(userDto.getStatus() == 1);
		this.setClientId(userDto.getClientId());
		if (userDto.getRoles() != null) {
			authorities = new ArrayList<>();
			userDto.getRoles().forEach(item -> authorities.add(new SimpleGrantedAuthority(item)));
		}
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}
