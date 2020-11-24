package com.soft1851.user.controller;

import com.soft1851.api.BaseController;
import com.soft1851.api.controller.user.FansControllerApi;
import com.soft1851.result.GraceResult;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName $(Name)
 * @Description TODO
 * @Author Qin jian
 * @Date 2020/11/17
 * @Version 1.0
 **/
@RestController
public class FansController extends BaseController implements FansControllerApi {
    @Override
    public GraceResult follow() {
        return GraceResult.ok("请求通过");
    }
}