package com.lnsoft.minio.controller;

import com.lnsoft.minio.enums.ResponseCode;
import com.lnsoft.minio.response.Response;
import com.lnsoft.minio.util.MinioTemplate;
import io.minio.MinioClient;
import io.minio.errors.*;
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
@RestController
@Slf4j
@RequestMapping("/minioTest")
public class MinioController {
@Autowired
    private MinioTemplate minioTemplate;

    /**
     * 上传文件
     * @param file 文件
     * @param bucketName 桶名称
     * @return
     */
    @PostMapping("/uploadFile")
    @ResponseBody
    public Response uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("bucketName") String bucketName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, InvalidArgumentException {
        log.info("MinioController[]uploadFile[]file:{}bucketName:{}",file,bucketName);
      return minioTemplate.uploadFile(file,bucketName);
    }

    /**
     * 下载文件
     * @param bucketName 桶名称
     * @param file 文件名称
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/downLoadFile")
    @ResponseBody
    public Response downLoadFile(@RequestParam(value = "bucketName") String bucketName, @RequestParam(required = true) String file, HttpServletRequest request, HttpServletResponse response){
        log.info("MinioController[]downLoadFile[]file:{}request:{}response:{}",file,request,response);
       return minioTemplate.downLoadFile(bucketName,file,request,response);
    }

}
