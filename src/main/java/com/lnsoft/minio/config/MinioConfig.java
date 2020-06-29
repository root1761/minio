package com.lnsoft.minio.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.lnsoft.minio.param.MinioParams;
import com.lnsoft.minio.util.MinioTemplate;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.mapstruct.BeforeMapping;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@Configuration
@EnableConfigurationProperties(MinioParams.class)
public class MinioConfig {
  private MinioParams minioParams;

    public MinioConfig(MinioParams minioParams) {
        this.minioParams = minioParams;
    }
@Bean
    public MinioClient getMinioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(minioParams.getEndpoint(),minioParams.getAccessKey(),minioParams.getSecretKey());
}
@Bean
    public MinioTemplate getMinioTemplate(){
        return new MinioTemplate();
}
}
