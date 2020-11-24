package com.soft1851.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName $(Name)
 * @Description TODO
 * @Author Qin jian
 * @Date 2020/11/17
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountInfoVO {
    private String id;
    private String mobile;
    private String nickname;
    private String face;
    private String realname;
    private String email;
    private Integer sex;
    private Date birthday;
    private String province;
    private String city;
    private String district;
}
