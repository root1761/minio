package com.lnsoft.minio.util;

import com.lnsoft.minio.enums.ResponseCode;
import com.lnsoft.minio.response.Response;
import io.minio.MinioClient;
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
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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

    public Response uploadFile(MultipartFile file, String bucketName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, InvalidArgumentException {
        log.info("MinioClient[]uploadFile[]file:{}bucketName:{}",file,bucketName);
        if (StringUtils.isEmpty(file)){
            return Response.no(ResponseCode.FILENAME_IS_NULL.getMessage());
        }
        if (StringUtils.isEmpty(bucketName)){
            return Response.no(ResponseCode.BUCKETNAME_IS_NULL.getMessage());
        }
        try {
            if ( minioClient.bucketExists(bucketName)){
                minioClient.putObject(bucketName,file.getOriginalFilename(),file.getInputStream(),file.getSize(),file.getContentType());
                return Response.yes();


            }else
                return Response.yes(ResponseCode.BUCKET_IS_NOT_EXIST.toString());
        }  catch (Exception e) {
            log.error("MinioClient[]uploadFile[]file:{}bucketName:{}cause:{}",file,bucketName,e);
            e.printStackTrace();
            return Response.no();
        }
    }
    public Response downLoadFile( String bucketName,  String file, HttpServletRequest request, HttpServletResponse response){
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
            response.setHeader("Content-Disposition","attachment;filename="+file);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(inputStream,response.getOutputStream());
            return Response.yes();
        }catch(Exception e){
            log.error("MinioClient[]downloadFile[]bucketName:{}file:{}cause:{}",bucketName,file,e);
            return Response.no(ResponseCode.FILE_IS_NOT_EXIST.getMessage());
        }

    }

}
