package com.lnsoft.minio;

import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@SpringBootTest
class MinioApplicationTests {
    @Test
    void contextLoads() throws InvalidPortException, InvalidEndpointException, IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, RegionConflictException {
      /*  MinioClient minioClient = new MinioClient("192.168.42.134:9000", "minioadmin", "minioadmin");
        boolean b = minioClient.bucketExists("test");
        if(b){
            log.error("bucket has Exsits");
        }else{
            minioClient.makeBucket("test");
        }*/




    }

}
