package com.soft1851.api.controller.user;

import com.soft1851.pojo.bo.UpdateUserInfoBO;
import com.soft1851.result.GraceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Qin Jian
 * @description TODO
 * @Data 2020/11/14
 */
@Api(value = "用户信息相关Controller",tags = {"用户信息相关Controller"})
@RequestMapping("user")
public interface UserControllerApi {

   /**
    * 获取所有用户
    *
    * @return
    */
   @ApiOperation(value = "获取所有用户信息", notes = "获取所有用户信息", httpMethod = "POST")
   @PostMapping("/all")
   GraceResult getAllUsers();

   /**
    * 获取用户基本信息
    *
    * @param userId
    * @return
    */
   @ApiOperation(value = "获取用户基本信息", notes = "获取用户基本信息", httpMethod = "POST")
   @PostMapping("/userInfo")
   GraceResult getUserInfo(@RequestParam String userId);


   /**
    * 更新用户账户信息
    * @param updateUserInfoBO
    * @param result
    * @return
    */
   @ApiOperation(value = "更新用户账户信息",notes = "更新用户账户信息",httpMethod = "POST")
   @PostMapping("/updateUserInfo")
   GraceResult updateUserInfo(@RequestBody @Valid UpdateUserInfoBO updateUserInfoBO, BindingResult result);

   /**
    * 获取用户基础信息
    *
    * @param userId
    * @return
    */
   @ApiOperation(value = "获取用户基础信息",notes = "获取用户基础信息",httpMethod = "POST")
   @PostMapping("/userBasicInfo")
   GraceResult getUserBasicInfo(@RequestParam String userId);
}
