package com.lnsoft.minio.controller;

import com.lnsoft.minio.enums.ResponseCode;
import com.lnsoft.minio.response.Response;
import com.lnsoft.minio.util.MinioTemplate;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author Louyp
 * @since 2020/04/21 23:20:00
 */
@Api(value="minio")
@RestController
@Slf4j
@RequestMapping("/minioOperation")
public class MinioController {
@Autowired
    private MinioTemplate minioTemplate;

    /**
     * 上传文件
     * @param file 文件
     * @param bucketName 桶名称
     * @return
     */
    @CrossOrigin
    @PostMapping("/uploadFile")
    @ApiOperation(value="上传文件",notes = "上传文件")
    public Response uploadFile(@RequestParam("pre") String pre,@RequestParam("file") MultipartFile file, @RequestParam("bucketName") String bucketName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, InvalidArgumentException {
        log.info("MinioController[]uploadFile[]file:{}bucketName:{}",file,bucketName);
      return minioTemplate.uploadFile(pre,file,bucketName);
    }

    /**
     * 下载文件
     * @param bucketName 桶名称
     * @param file 文件名称
     * @param request
     * @param response
     * @return
     */
    @CrossOrigin
    @ApiOperation(value="下载文件",notes = "下载文件")
    @PostMapping("/downloadFile")
    public Response downloadFile(@RequestParam(value = "bucketName") String bucketName, @RequestParam(required = true) String file, HttpServletRequest request, HttpServletResponse response){
        log.info("MinioController[]downLoadFile[]file:{}request:{}response:{}",file,request,response);
       return minioTemplate.downloadFile(bucketName,file,request,response);
    }

    /**
     * 创建桶
     * @param bucketName 桶名称
     * @return
     */
    @ApiOperation(value="创建桶",notes = "创建桶")
    @CrossOrigin
    @PostMapping("/createBucket")
    public Response createBucket(@RequestParam String bucketName){
        log.info("MinioController[]createBucket[]bucketName:{}",bucketName);
        return minioTemplate.createBucket(bucketName);
    }

    /**
     * 删除桶
     * @param bucketName 桶名称
     * @return
     */
    @CrossOrigin
    @PostMapping("/removeBucket")
    public Response removeBucket(@RequestParam String bucketName){
        log.info("MinioController[]removeBucket[]bucketName:{}",bucketName);
        return minioTemplate.deleteBucket(bucketName);
}

    /**
     * 删除文件
     * @param bucketName
     * @param fileName
     * @return
     */
    @CrossOrigin
    @PostMapping("/removeFileObject")
    public Response removeFileObject(@RequestParam String bucketName,@RequestParam String fileName){
        log.info("MinioController[]removeFileObject[]bucketName:{}fileName:{}",bucketName,fileName);
        return minioTemplate.deleteObject(bucketName,fileName);
    }


}
