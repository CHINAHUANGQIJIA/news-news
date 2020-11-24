package com.soft1851.files.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName $(Name)
 * @Description TODO
 * @Author qin jian
 * @Date 2020/11/19
 * @Version 1.0
 **/
public interface UploadService {
    /**
     * fdfs上传
     *
     * @param  file 文件
     * @param fileExtName 扩展名
     * @return url
     * @throws Exception 异常
     */
    String uploadFdfs(MultipartFile file, String fileExtName) throws Exception;

    /**
     * OSS文件上传
     *
     * @param  file 文件
     * @param userId 用户id
     * @param fileExtName 扩展名
     * @return 返回
     * @throws Exception 异常
     */
    public String uploadOSS(MultipartFile file, String userId, String fileExtName) throws Exception;

}
