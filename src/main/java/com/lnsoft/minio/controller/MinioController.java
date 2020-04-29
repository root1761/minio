package com.lnsoft.minio.controller;

import com.lnsoft.minio.enums.ResponseCode;
import com.lnsoft.minio.response.Response;
import com.lnsoft.minio.util.MinioTemplate;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.swagger.annotations.*;
import jdk.nashorn.internal.ir.annotations.Ignore;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pre",value = "前缀",paramType = "query",required = true),
            @ApiImplicitParam(name="bucketName",value = "桶名",paramType = "query",required = true)
    })
    public Response uploadFile(String pre,@ApiParam(required = true,name = "file",value = "文件") @RequestParam MultipartFile file, String bucketName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, InvalidArgumentException {
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bucketName",value = "桶名",paramType = "query",required = true),
            @ApiImplicitParam(name = "file",value = "文件名",paramType = "query",required = true)
    })
    public Response downloadFile( String bucketName, String file, @Ignore HttpServletRequest request,@Ignore HttpServletResponse response){
        log.info("MinioController[]downLoadFile[]bucketName:{}file:{}request:{}response:{}",bucketName,file,request,response);
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
    @ApiImplicitParam(name = "bucketName",value = "桶名",paramType = "query",required = true)
    public Response createBucket( String bucketName){
        log.info("MinioController[]createBucket[]bucketName:{}",bucketName);
        return minioTemplate.createBucket(bucketName);
    }

    /**
     * 删除桶
     * @param bucketName 桶名称
     * @return
     */
    @ApiOperation(value="删除桶",notes="删除桶")
    @CrossOrigin
    @PostMapping("/removeBucket")
    @ApiImplicitParam(name = "bucketName",value = "桶名",paramType = "query",required = true)
    public Response removeBucket( String bucketName){
        log.info("MinioController[]removeBucket[]bucketName:{}",bucketName);
        return minioTemplate.deleteBucket(bucketName);
}

    /**
     * 删除文件
     * @param bucketName
     * @param fileName
     * @return
     */
    @ApiOperation(value="删除文件",notes="删除文件")
    @CrossOrigin
    @PostMapping("/removeFileObject")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bucketName",value = "桶名",paramType = "query",required = true),
            @ApiImplicitParam(name = "fileName",value = "文件名",paramType = "query",required = true)
    })
    public Response removeFileObject( String bucketName, String fileName){
        log.info("MinioController[]removeFileObject[]bucketName:{}fileName:{}",bucketName,fileName);
        return minioTemplate.deleteObject(bucketName,fileName);
    }


}
