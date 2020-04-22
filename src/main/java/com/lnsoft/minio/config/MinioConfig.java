package com.lnsoft.minio.config;

import com.lnsoft.minio.param.MinioParams;
import com.lnsoft.minio.util.MinioTemplate;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinioParams.class)
public class MinioConfig {
  private MinioParams minioParams;

    public MinioConfig(MinioParams minioParams) {
        this.minioParams = minioParams;
    }
@Bean
    public MinioClient getMinioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(minioParams.getEndpoint(),minioParams.getAccressKey(),minioParams.getSecretKey());
}
@Bean
    public MinioTemplate getMinioTemplate(){
        return new MinioTemplate();
}

}
