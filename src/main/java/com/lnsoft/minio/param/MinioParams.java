package com.lnsoft.minio.param;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Louyp
 * @version 1.0
 * @data 2020/04/22 15:11
 */
@Component
@PropertySource(value = "classpath:minio.properties")
@ConfigurationProperties(prefix = "minio")
@Getter
@Setter
public class MinioParams {
    private  String  endpoint;
    private  String accressKey;
    private  String secretKey;
}
