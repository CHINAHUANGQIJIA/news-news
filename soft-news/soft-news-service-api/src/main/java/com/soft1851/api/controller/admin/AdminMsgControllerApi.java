package com.soft1851.api.controller.admin;

import com.soft1851.pojo.bo.AdminLoginBO;
import com.soft1851.pojo.bo.NewAdminBO;
import com.soft1851.result.GraceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName $(Name)
 * @Description TODO
 * @Author qin jian
 * @Date 2020/11/20
 * @Version 1.0
 **/

@Api(value = "管理员维护",tags = {"管理员维护controller"})
@RequestMapping("adminMsg")
public interface AdminMsgControllerApi {

    /**
     * 管理员登录
     * @param request 请求
     * @param response 响应
     * @param adminLoginBO 管理员登录BO类
     * @throws Exception 异常
     */

    @PostMapping("/adminLogin")
    @ApiOperation(value = "管理员登录",notes = "管理员登录",httpMethod = "POST")
    GraceResult adminLogin(@RequestBody AdminLoginBO adminLoginBO, HttpServletRequest request, HttpServletResponse response);

    /**
     * 查询管理员是否存在
     * @param username 管理员用户名
     * @throws GraceResult
     */

    @PostMapping("/adminIsExist")
    @ApiOperation(value = "查询管理员是否存在",notes = "查询管理员是否存在",httpMethod = "POST")
    GraceResult adminIsExist(@RequestParam String username);

    /**
     * 新增管理员
     * @param request 请求
     * @param response 响应
     * @paramnewAdminBO BO入参
     * @return 结果
     */
    @PostMapping("addNewAdmin")
    @ApiOperation(value = "添加新的管理人员",notes = "添加新的管理人员",httpMethod = "POST")
    GraceResult addNewAdmin(HttpServletResponse response, HttpServletRequest request, @RequestBody NewAdminBO newAdminBO);

    /**
     * 查询admin列表
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询admin列表",notes = "查询admin列表",httpMethod = "POST")
    @PostMapping("/getAdminList")
    GraceResult getAdminList(
            @ApiParam(name="page",value = "查询下一页的第几页")
            @RequestParam Integer page,
            @ApiParam(name="pageSize", value = "分页查询每一页显示的条数")
            @RequestParam Integer pageSize);

    /**
     * 退出登录
     * @param adminId 管理员id
     * @param response 响应
     * @param request 请求
     * @return 返回
     */
    @PostMapping("adminLogout")
    @ApiOperation(value = "管理员注销",notes = "管理员注销",httpMethod = "POST")
    GraceResult adminLogout(@RequestParam String adminId,HttpServletRequest request,HttpServletResponse response);

    /**
     * 修改管理员信息
     * @param newAdminBO 入参
     * @param response 响应
     * @param request 请求
     * @return 返回
     */
    @PostMapping("updateAdmin")
    @ApiOperation(value = "修改管理员信息", notes = "修改管理员信息", httpMethod = "POST")
    GraceResult updateAdmin(HttpServletRequest request,HttpServletResponse response, @RequestBody NewAdminBO newAdminBO );

    /**
     * 管理员人脸登录
     *
     * @param adminLoginBO 入参
     * @param request 请求
     * @param response 响应
     * @return 返回
     */
    @ApiOperation(value = "管理员人脸登录",notes = "管理员人脸登录",httpMethod = "POST")
    @PostMapping("/adminFaceLogin")
    GraceResult adminFaceLogin(@RequestBody AdminLoginBO adminLoginBO,
                               HttpServletRequest request,
                               HttpServletResponse response);

}
