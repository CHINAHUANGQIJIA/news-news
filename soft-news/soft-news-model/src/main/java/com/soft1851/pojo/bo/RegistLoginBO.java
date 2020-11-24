package com.soft1851.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName $(Name)
 * @Description TODO
 * @Author Qin jian
 * @Date 2020/11/16
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistLoginBO {

    /**
     * @NotNull 只校验Null值，@NotBlank 会通知校验Null和""串
     */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @NotBlank(message = "短信验证码不能为空")
    private String smsCode;
}
