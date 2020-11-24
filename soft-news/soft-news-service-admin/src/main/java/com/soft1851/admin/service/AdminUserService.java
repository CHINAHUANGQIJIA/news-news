package com.soft1851.admin.service;

import com.soft1851.pojo.AdminUser;
import com.soft1851.pojo.bo.NewAdminBO;
import com.soft1851.utils.PageGridResult;

import java.util.List;

/**
 * @ClassName $(Name)
 * @Description TODO
 * @Author qin jian
 * @Date 2020/11/20
 * @Version 1.0
 **/
public interface AdminUserService {
    /**
     * 获得管理员用户信息
     * @param username
     * @return
     */
    AdminUser queryAdminByUsername(String username);

    /**
     * 新增管理员
     * @param newAdminBO
     */
    void createAdminUser(NewAdminBO newAdminBO);

    /**
     * 分页查询管理员列表
     * @param page
     * @param pageSize
     * @return
     */
    PageGridResult queryAdminList(Integer page, Integer pageSize);

    PageGridResult setterPageGrid(List<?> adminUserList, Integer page);
    /**
     * 修改指定管理员的faceId
     * @param username 用户名
     * @param faceId faceid
     * @return
     */
    void updateAdmin(String username,String faceId);

}