package com.ada.auth.service;

import com.ada.auth.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 用户认证逻辑层
 * 使用@Feign调用ada-admin中的获取用户的逻辑
 *
 * @ClassName : UserService
 * @date :2021/10/8  22:58
 * @author : Ada
 */
@FeignClient("ada-admin")
public interface UserService {

	/**
	 *  调用ada-admin模块中的url为/admin/loadByUserName的请求
	 *  使用的是feign的声明式调用
	 */
	@GetMapping("/admin/loadByUserName")
	UserDto loadUserByUsername(@RequestParam String username);
}
