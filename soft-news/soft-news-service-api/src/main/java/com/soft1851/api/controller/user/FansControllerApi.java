package com.soft1851.api.controller.user;

import com.soft1851.result.GraceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName $(Name)
 * @Description TODO
 * @Author qin jian
 * @Date 2020/11/17
 * @Version 1.0
 **/
@Api(value = "粉丝信息相关Controller",tags = {"粉丝信息相关Controller"})
@RequestMapping("fans")
public interface FansControllerApi {
    /**
     * 获取粉丝基础信息
     *
     *
     * @return
     */
    @ApiOperation(value = "获取粉丝基础信息",notes = "获取粉丝基础信息",httpMethod = "POST")
    @PostMapping("/follow")
    GraceResult follow();
}

