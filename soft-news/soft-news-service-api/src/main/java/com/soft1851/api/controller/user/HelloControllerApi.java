package com.soft1851.api.controller.user;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName $(Name)
 * @Description TODO
 * @Author qin jian
 * @Date 2020/11/13
 * @Version 1.0
 **/
public interface HelloControllerApi {
    /**
     * hello接口
     *
     * @return Object
     */
    @GetMapping("/hello")
    Object hello();
}
