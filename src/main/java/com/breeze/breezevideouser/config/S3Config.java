package com.breeze.breezevideouser.config;

import com.breeze.breezevideouser.utils.YamlUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * @author breeze
 * @date 2022/12/21 03:05
 */

@Configuration
@Getter
public class S3Config {
    private final String accessKey = YamlUtil.getStringByYaml("aws.access-key");

    private final String secretKey = YamlUtil.getStringByYaml("aws.secret-key");

    private final String region = YamlUtil.getStringByYaml("aws.region");

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder().region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build();
    }

}
