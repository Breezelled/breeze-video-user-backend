package com.breeze.breezevideouser.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.breeze.breezevideouser.common.ApiResponse;
import com.breeze.breezevideouser.config.S3Config;
import com.breeze.breezevideouser.log.annotation.WebLog;
import com.breeze.breezevideouser.utils.S3Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

/**
 * @author breeze
 * @date 2022/12/21 08:24
 */
@RestController
@RequestMapping("/s3")
public class S3Controller {

    final Logger logger = LoggerFactory.getLogger(S3Controller.class);

    private final S3Config s3Config = new S3Config();

    @Autowired
    private S3Client s3Client;

    private final Region region = Region.of(s3Config.getRegion());

    private final AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(s3Config.getAccessKey(),
            s3Config.getSecretKey());
    private final S3Presigner s3Presigner = S3Presigner.builder().region(region)
            .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials)).build();

    @WebLog(description = "S3上传文件")
    @PostMapping
    public ApiResponse uploadFile(@RequestParam("file") MultipartFile multipartFile,
                                  @RequestParam("bucketName") String bucketName,
                                  @RequestParam(value = "fileKey", required = false) String fileKey
    ) throws Exception {
        String fileUrl = "";
        File file = S3Util.convertMultiPartToFile(multipartFile);
        try {
            String fileName = multipartFile.getOriginalFilename();
            if (StringUtils.isBlank(fileKey)) {
                fileKey = fileName;
            }
            S3Util.uploadObject(s3Client, bucketName, fileKey, file.getAbsolutePath());
            URL url = S3Util.getPresignedUrl(s3Presigner, bucketName, fileKey);
            fileUrl = url.toString();
        } catch (Exception e) {
            return ApiResponse.error("Upload Failed");
        }
        HashMap<Object, Object> map = new HashMap<>();
        map.put("fileUrl", fileUrl);
        return ApiResponse.ok("Upload Succeeded", map);
    }

    @WebLog(description = "S3获取对象Url")
    @GetMapping
    public ApiResponse getObjectByName(@RequestParam("bucketName") String bucketName,
                           @RequestParam("objectName") String objectName) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("fileUrl", S3Util.getPresignedUrl(s3Presigner, bucketName, objectName));
        return ApiResponse.ok(map);
    }

    @WebLog(description = "S3获取对象Url")
    @GetMapping("url")
    public ApiResponse getObjectUrl(@RequestParam("objectName") String objectName) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("fileUrl", "s3/" + objectName);
        return ApiResponse.ok(map);
    }

}
