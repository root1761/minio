package com.lnsoft.minio.util;

import com.lnsoft.minio.enums.ResponseCode;
import com.lnsoft.minio.response.Response;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.errors.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author Louyp
 * @version 1.0
 * @data 2020/04/22 13:24
 */

@Component
@Slf4j

public class MinioTemplate {

    @Autowired
    private  MinioClient minioClient;

    /**
     *上传文件
     * @param pre 日期拼接
     * @param file 文件
     * @param bucketName 桶名称
     * @return
     */
    public Response uploadFile(String pre,MultipartFile file, String bucketName)  {
        log.info("MinioClient[]uploadFile[]pre:{}file:{}bucketName:{}",pre,file,bucketName);
        if (StringUtils.isEmpty(file)){
            return Response.no(ResponseCode.FILENAME_IS_NULL.getMessage());
        }
        if (StringUtils.isEmpty(bucketName)){
            return Response.no(ResponseCode.BUCKETNAME_IS_NULL.getMessage());
        }
        try {
            if ( minioClient.bucketExists(bucketName)){
                minioClient.putObject(bucketName,pre+file.getOriginalFilename(),file.getInputStream(),file.getSize(),file.getContentType());
                return Response.yes();


            }else
                return Response.yes(ResponseCode.BUCKET_IS_NOT_EXIST.toString());
        }  catch (Exception e) {
            log.error("MinioClient[]uploadFile[]file:{}bucketName:{}cause:{}",file,bucketName,e);
            return Response.no();
        }
    }

    /**
     *下载文件
     * @param bucketName 桶名称
     * @param file 文件名称
     * @param request
     * @param response
     * @return
     */
    public Response downloadFile( String bucketName,  String file, HttpServletRequest request, HttpServletResponse response){
        log.info("MinioClient[]downLoadFile[]file:{}request:{}response:{}",file,request,response);
        if (StringUtils.isEmpty(file)){
            return Response.no(ResponseCode.FILENAME_IS_NULL.getMessage());
        }
        if (StringUtils.isEmpty(bucketName)){
            return Response.no(ResponseCode.BUCKETNAME_IS_NULL.getMessage());
        }
        String userAgent = request.getHeader("User-Agent");
        try(InputStream inputStream =minioClient.getObject(bucketName,file)){
            String fileName=null;
            if(userAgent.toUpperCase().contains("MSIE")||userAgent.contains("Trident/7.0")){
                fileName= URLEncoder.encode(file, "UTF-8");

            }else{
                fileName=new String(file.getBytes("UTF-8"),"iso-8859-1");
            }
            response.setHeader("Content-Disposition","attachment;filename="+fileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(inputStream,response.getOutputStream());
            return Response.yes();
        }catch(Exception e){
            log.error("MinioClient[]downloadFile[]bucketName:{}file:{}cause:{}",bucketName,file,e);
            return Response.no(ResponseCode.FILE_IS_NOT_EXIST.getMessage());
        }

    }

    /**
     *创建桶
     * @param bucketName 桶名称
     * @return
     *
     *
     */
    public Response createBucket(String bucketName){
        log.info("MinioTemplate[]createBucket[]bucketName:{}",bucketName);
        if (StringUtils.isEmpty(bucketName)){
            return Response.no(ResponseCode.BUCKETNAME_IS_NULL.getMessage());
        }
        try {
        if (minioClient.bucketExists(bucketName)){
            return Response.no(ResponseCode.BUCKET_IS_EXIST.getMessage());
        }

            minioClient.makeBucket(bucketName);
            return Response.yes();
        }catch (Exception e) {
            log.error("MinioTemplate[]createBucket[]bucketName:{}cause:{}",bucketName,e);
            return Response.no(ResponseCode.BUCKET_CREATE_FAILED.getMessage());
        }
    }

    /**
     * 删除桶
     * @param bucketName 桶名称
     * @return
     */
    public Response deleteBucket(String bucketName) {
        log.info("MinioTemplate[]deleteBucket[]bucketName:{}",bucketName);
        if(StringUtils.isEmpty(bucketName)){
            return Response.no(ResponseCode.BUCKETNAME_IS_NULL.getMessage());
        }

        try {
            if (!minioClient.bucketExists(bucketName)){
                return Response.no(ResponseCode.BUCKET_IS_NOT_EXIST.getMessage());
            }
            minioClient.removeBucket(bucketName);
            return Response.yes();
        }  catch (Exception e) {
         log.error("MinioTemplate[]deleteBucket[]bucketName:{}cause:{}",bucketName,e);
            return Response.no(ResponseCode.BUCKET_DELETE_FAILED.getMessage());
        }
    }

    /**
     * 删除文件
     * @param bucketName 桶名称
     * @param fileName 文件名称
     * @return
     */
    public Response deleteObject(String bucketName,String fileName){
        log.info("MinioTemplate[]deleteObject[]fileName:{}",fileName);
        if (StringUtils.isEmpty(bucketName)){
            return Response.no(ResponseCode.BUCKETNAME_IS_NULL.getMessage());
        }
        if (StringUtils.isEmpty(fileName)){
            return Response.no(ResponseCode.FILENAME_IS_NULL.getMessage());
        }
        try{
            minioClient.statObject(bucketName, fileName);
            minioClient.removeObject(bucketName,fileName);
            return Response.yes();
        }catch(Exception e){
            log.error("MinioTemplate[]deleteObject[]bucketName:{}fileName:{}cause:{}",bucketName,fileName,e);
            return Response.no(ResponseCode.FILE_DELETE_FAILED.getMessage());
        }
    }
    public Response uploadFiles(String bucketName) {
        log.info("MinioTemplete[]uploadFiles[]bucketName:{}",bucketName);
        File file=new File("/home/gdfw/"+bucketName);
        final File[] files = file.listFiles();
        try {
            for (File file1:files){
                InputStream inputStream=new FileInputStream(file1);
                minioClient.putObject(bucketName,file1.getName(),inputStream,new MimetypesFileTypeMap().getContentType(file1));
            }
            return Response.yes();
        } catch (Exception e) {
            log.error("MinioTemplate[]uploadFiles[]bucketName:{}cause:{}",bucketName,e);
            return Response.no();
        }
    }

}
