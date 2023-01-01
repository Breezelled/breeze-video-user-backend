package com.breeze.breezevideouser.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.breeze.breezevideouser.controller.S3Controller;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import software.amazon.awssdk.utils.IoUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author breeze
 * @date 2022/12/21 10:07
 */
@Getter
public class S3Util {

    private final String accessKey = YamlUtil.getStringByYaml("aws.access-key");
    private final String secretKey = YamlUtil.getStringByYaml("aws.secret-key");
    private final String region = YamlUtil.getStringByYaml("aws.region");

    @Autowired
    private static S3Client s3Client;

    public String generatorObjectName(String fileName) {
        String objectName = IdUtil.objectId();
        String extName = FileUtil.extName(fileName);
        return objectName+"."+extName;
    }

    public static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


    public static URL generatePresignedUrl(S3Presigner s3Presigner, String bucketName,
                                           String objectKey, Region region, String accessKeyId,
                                           String secretAccessKey, long presignUrlDurationMinutesLong) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(objectKey).build();
        PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(presignUrlDurationMinutesLong))
                .putObjectRequest(putObjectRequest)
                .build();
        PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(putObjectPresignRequest);
        URL presignedUrl = presignedPutObjectRequest.url();
        s3Presigner.close();
        return presignedUrl;
    }


    public static URL getPresignedUrl(S3Presigner presigner, String bucketName, String keyName) {
        try {
            GetObjectRequest getObjectRequest =
                    GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(keyName)
                            .build();
            GetObjectPresignRequest getObjectPresignRequest =
                    GetObjectPresignRequest.builder()
                            .signatureDuration(Duration.ofMinutes(1440))
                            .getObjectRequest(getObjectRequest)
                            .build();
            PresignedGetObjectRequest presignedGetObjectRequest =
                    presigner.presignGetObject(getObjectPresignRequest);
            HttpURLConnection connection = (HttpURLConnection) presignedGetObjectRequest.url().openConnection();
            presignedGetObjectRequest.httpRequest().headers().forEach((header, values) -> {
                values.forEach(value -> {
                    connection.addRequestProperty(header, value);
                });
            });
            if (presignedGetObjectRequest.signedPayload().isPresent()) {
                connection.setDoOutput(true);
                try (InputStream signedPayload = presignedGetObjectRequest.signedPayload().get().asInputStream();
                     OutputStream httpOutputStream = connection.getOutputStream()) {
                    IoUtils.copy(signedPayload, httpOutputStream);
                }
            }
            try (InputStream content = connection.getInputStream()) {
                IoUtils.copy(content, System.out);
            }
            presigner.close();
            return presignedGetObjectRequest.url();
        } catch (S3Exception | IOException e) {
            System.err.println(e);
        }
        return null;
    }

    public static String uploadObject(S3Client s3, String bucketName, String objectKey, String objectPath) {

        try {
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .contentType("image/png;image/jpg;image/git")
                    .build();

            PutObjectResponse response = s3.putObject(putOb, RequestBody.fromBytes(getObjectFile(objectPath)));
            return response.eTag();

        } catch (S3Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        return "";
    }

    // Return a byte array.
    private static byte[] getObjectFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {
            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bytesArray;
    }



}
