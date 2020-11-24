package com.soft1851.files.controller;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;
import com.soft1851.api.BaseController;
import com.soft1851.api.controller.files.FileUploadControllerApi;
import com.soft1851.exception.GraceException;
import com.soft1851.files.resource.FileResource;
import com.soft1851.files.service.UploadService;
import com.soft1851.result.GraceResult;
import com.soft1851.result.ResponseStatusEnum;
import com.soft1851.utils.FileUtil;
import com.soft1851.utils.extend.AliImageReviewUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Qin Jian
 * @description TODO
 * @Data 2020/11/19
 */
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileUploadController implements FileUploadControllerApi {

    private final UploadService uploadService;
    private final FileResource fileResource;
    private final AliImageReviewUtil aliImageReviewUtil;
    private final GridFSBucket gridFSBucket;
    private final GridFsTemplate gridFsTemplate;


    @Override
    public GraceResult uploadFace(String userId, MultipartFile file) throws Exception {
        String path;
        if(file != null){
            //获得文件上传名称
            String fileName = file.getOriginalFilename();
            //判断文件名不能为空
            if(StringUtils.isNotBlank(fileName)){
                // 分割命名
                String[] fileNameArr = fileName.split("\\.");
                //获得后端
                String suffix = fileNameArr[fileNameArr.length-1];
                //判断后缀是否符合我们的预定义范围
                if(!"png".equalsIgnoreCase(suffix)&&!"jpg".equalsIgnoreCase(suffix)&&
                        !"jpeg".equalsIgnoreCase(suffix)){
                    return GraceResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_NULL_ERROR);
                }
                //执行上传服务，得到回调路径
                path = uploadService.uploadFdfs(file,suffix);
            }else {
                return GraceResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_NULL_ERROR);
            }
            log.info("path = "+path);
            String finalPath;
            if(StringUtils.isNotBlank(path)){
                finalPath = fileResource.getHost()+path;
            }else {
                return GraceResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
            }
            return GraceResult.ok(doAliimageReview(finalPath));
        }
        return null;
    }


    /**
     * 检测不通过的默认图片
     */
    public static final String FAILED_IMAGE_URL = "https://sillyforce.oss-cn-beijing.aliyuncs.com/soft-news/201119BXRRMTGN54.jpg";

    /**
     * 阿里云图片智能检测
     *
     * @param pendingImageUrl 图片路径
     * @return 返回结果
     */
    private String doAliimageReview(String pendingImageUrl) {
        log.info(pendingImageUrl);
        boolean result = false;
        try {
            result = aliImageReviewUtil.reviewImage(pendingImageUrl);
        } catch (Exception e) {
            System.err.println("图片识别出错");
        }
        if (!result) {
            return  FAILED_IMAGE_URL;
        }
        return pendingImageUrl;
    }

    @Override
    public GraceResult uploadSomeFiles(String userId, MultipartFile[] files) throws Exception {
       // 声明list，用于存放多个图片的地址路径，返回到前端
        List<String> imageUrlList = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String path;
                if (file != null) {
                    //获得文件上传的名称
                    String fileName = file.getOriginalFilename();
                    //判断文件名不能为空
                    if (StringUtils.isNoneBlank(fileName)) {
                        String[] fileNameArr = fileName.split("\\.");
                        //获得后缀
                        String suffix = fileNameArr[fileNameArr.length - 1];
                        //判断后缀符合我们的预定义规范
                        if (!"png".equalsIgnoreCase(suffix) && !"jpg".equalsIgnoreCase(suffix) &&
                                !"jpeg".equalsIgnoreCase(suffix)) {
                            continue;
                        }
                        //执行上传服务，得到回调路径
                        path = uploadService.uploadOSS(file, userId, suffix);
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
                String finalPath;
                if (StringUtils.isNoneBlank(path)) {
                    finalPath = fileResource.getOssHost() + path;
                    // TOOD: 2020/11/19 : 后缀需要对图片做一次审核
                    imageUrlList.add(finalPath);
                }
            }
        }
            return GraceResult.ok(imageUrlList);
        }

    @Override
    public GraceResult uploadToGridFs(String username, MultipartFile multipartFile){
        // 设置meta数据值
        Map<String, String> metaData = new HashMap<>(8);
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取文件的源名称
        String fileName = multipartFile.getOriginalFilename();
        // 进行文件存储
        assert inputStream != null;
        ObjectId objectId = gridFsTemplate.store(inputStream, fileName, metaData);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GraceResult.ok(objectId.toHexString());
    }

    @Override
    public GraceResult readFace64(String faceId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 0.获得gridfs中人脸文件
        File myFace = readFileFromGridFs(faceId);
        // 1.转换人脸为base64
        String base64Face = FileUtil.fileToBase64(myFace);
        return GraceResult.ok(base64Face);
    }

    private File readFileFromGridFs(String faceId) throws Exception {
        GridFSFindIterable files = gridFSBucket.find(Filters.eq("_id", new ObjectId(faceId)));
        GridFSFile gridFsFile = files.first();
        if (gridFsFile == null) {
            GraceException.display(ResponseStatusEnum.FILE_NOT_EXIST_ERROR);
        }
        String fileName = gridFsFile.getFilename();
        System.out.println(fileName);
        // 获取文件流，保存文件到本地或者服务器的临时目录
        File fileTemp = new File("/Users/shahuhudexiaobinggan/Documents/temp_face");
        if (!fileTemp.exists()) {
            fileTemp.mkdirs();
        }
        File myFile = new File("/Users/shahuhudexiaobinggan/Documents/temp_face/" + fileName);
        // 创建文件输出流
        OutputStream os = new FileOutputStream(myFile);
        // 下载到服务器或者本地
        gridFSBucket.downloadToStream(new ObjectId(faceId),os);
        return myFile;
    }

    @Override
    public GraceResult readInGridFs(String faceId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(faceId)));
        if (gridFSFile == null) {
            throw new RuntimeException("No file with id: " + faceId);
        }
        System.out.println(gridFSFile.getFilename());
        //获取流对象
        GridFsResource resource = gridFsTemplate.getResource(gridFSFile);
        InputStream inputStream;
        String content = null;
        byte[] bytes = new byte[(int) gridFSFile.getLength()];
        try {
            inputStream = resource.getInputStream();
            inputStream.read(bytes);
            inputStream.close();
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GraceResult.ok(new String(bytes));
    }


}