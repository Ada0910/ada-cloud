package com.ada.auth.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 登录用户对象
 *
 * @ClassName : UserDto
 * @date :2021/10/23  21:09
 * @author : Ada
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Integer status;
    private String clientId;
    private List<String> roles;

}
