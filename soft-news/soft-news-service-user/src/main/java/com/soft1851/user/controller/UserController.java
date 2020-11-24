package com.soft1851.user.controller;

import com.soft1851.api.BaseController;
import com.soft1851.api.controller.user.UserControllerApi;
import com.soft1851.pojo.AppUser;
import com.soft1851.pojo.bo.UpdateUserInfoBO;
import com.soft1851.pojo.vo.AppUserVO;
import com.soft1851.pojo.vo.UserAccountInfoVO;
import com.soft1851.result.GraceResult;
import com.soft1851.result.ResponseStatusEnum;
import com.soft1851.user.mapper.AppUserMapper;
import com.soft1851.user.service.UserService;
import com.soft1851.utils.JsonUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * @ClassName $(Name)
 * @Description TODO
 * @Author Qin jian
 * @Date 2020/11/14
 * @Version 1.0
 **/
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController extends BaseController implements UserControllerApi {

    private final UserService userService;

    @Override
    public GraceResult getAllUsers() {
        return null;
    }

    @Override
    public GraceResult getUserInfo(String userId) {
        //0、判断不能为空
        if(StringUtils.isBlank(userId)){
            return GraceResult.errorCustom(ResponseStatusEnum.UN_LOGIN);
        }
        //1、根据userId查询用户，调用内部封装方法
        AppUser user = getUser(userId);
        //2.设置VO——需要展示的信息
        UserAccountInfoVO accountInfoVO = new UserAccountInfoVO();
        //3、属性拷贝
        BeanUtils.copyProperties(user,accountInfoVO);
        return GraceResult.ok(accountInfoVO);
    }

    @Override
    public GraceResult updateUserInfo(@Valid UpdateUserInfoBO updateUserInfoBO, BindingResult result) {
        //判断BindingResult是否保存错误的验证信息，如果有，直接return
        if(result.hasErrors()){
            Map<String,String> errorMap = getErrors(result);
            return GraceResult.errorMap(errorMap);
        }
        //执行更新用户信息操作
        userService.updateUserInfo(updateUserInfoBO);
        return GraceResult.ok();
    }

    @Override
    public GraceResult getUserBasicInfo(String userId) {
        //0、判断不能为空
        if(StringUtils.isBlank(userId)){
            return GraceResult.errorCustom(ResponseStatusEnum.UN_LOGIN);
        }
        //1、查询userId
        AppUser user = getUser(userId);
        //2、信息脱敏，设置不显示
        AppUserVO userVo = new AppUserVO();
        BeanUtils.copyProperties(user,userVo);
        return GraceResult.ok(userVo);
    }

    private AppUser getUser(String userId){
        // 1.查询redis中是否包含用户信息，如果包含则查询redis返回，如果不包含则查询数据库
        String userJson = redis.get(REDIS_USER_INFO + ":" + userId);
        AppUser user;
        if (StringUtils.isNoneBlank(userJson)) {
            user = JsonUtil.jsonToPojo(userJson, AppUser.class);
        } else {
            user = userService.getUser(userId);
            // 2. 由于用户信息不怎么会变动，这类信息数据不会去查询数据库，完全可以把用户信息存入redis
            // 哪怕修改信息，也不会立马体现，这也是弱一致性，在这里有过期时间，到时间用户信息会更新到页面显示
            // 基本信息是属于数据一致性优先级比较低的，用户眼里的主要以内容为主，至于文章是谁发的，一般来说不会过多关注
            redis.set(REDIS_USER_INFO + ":" + userId, JsonUtil.objectToJson(user), 1);
        }
        return user;
    }

}
