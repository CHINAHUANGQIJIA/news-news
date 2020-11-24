package com.soft1851.user.service;

import com.soft1851.pojo.AppUser;
import com.soft1851.pojo.bo.UpdateUserInfoBO;

/**
 * @author wl_sun
 * @description TODO
 * @Data 2020/11/16
 */
public interface UserService {


    /**
     * 判断用户是否存在，如果存在返回user信息
     *
     * @param mobile
     * @return
     */
    AppUser queryMobileIsExist(String mobile);

    /**
     * 创建用户，新增用户记录到用户表
     *
     * @param mobile
     * @return
     */
    AppUser createUser(String mobile);


    /**
     * 根据用户主键获取用户信息
     *
     * @param userId
     * @return
     */
    AppUser getUser(String userId);

    /**
     * 更新用户信息
     *
     * @param updateUserInfoBO
     */
    void updateUserInfo(UpdateUserInfoBO updateUserInfoBO);

}
