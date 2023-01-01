package com.breeze.breezevideouser.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import software.amazon.awssdk.services.s3.model.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breeze.breezevideouser.DO.Info;
import com.breeze.breezevideouser.DO.S3;
import com.breeze.breezevideouser.service.InfoService;
import com.breeze.breezevideouser.service.S3Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.File;
import java.util.*;

/**
 * @author breeze
 * @date 2022/12/22 17:08
 */
@SpringBootTest
class S3ControllerTest {

    @Autowired
    InfoService infoService;

    @Autowired
    S3Service s3Service;

    @Autowired
    S3Client s3Client;


    @Test
    void isNoneUploaded(){

        CsvReader reader = CsvUtil.getReader();
        CsvData data = reader.read(FileUtil.file("/Users/breeze/XMUT/THESIS/backend/breeze-video-user/src/test/java/com/breeze/breezevideouser/controller/links.csv"));
        File[] files = FileUtil.ls("/Users/breeze/XMUT/BD/backend/IMDB/trailer");
        List<CsvRow> rows = data.getRows();
        int cnt = 0;
        List<String> noneUploadedList = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        for (CsvRow csvRow : rows) {
//            System.out.println(csvRow.getRawList());
            String row = csvRow.get(0);
            String[] s = row.split("\\s+");
            System.out.println(Arrays.toString(s));
            fileNames.add(s[3]);
            cnt++;
        }
        System.out.println(cnt);
        for (File f : files) {
            if (!fileNames.contains(f.getName()) && "mp4".equals(FileUtil.extName(f))) {
                noneUploadedList.add(f.getName());
            }
        }
        S3Controller s3Controller = new S3Controller();
        for (String s : noneUploadedList) {
            File file = FileUtil.file("/Users/breeze/XMUT/BD/backend/IMDB/trailer/" + s);
            FileUtil.copy("/Users/breeze/XMUT/BD/backend/IMDB/trailer/" + s,
                    "/Users/breeze/XMUT/THESIS/backend/breeze-video-user/src/test/java/com/breeze/breezevideouser/controller/trailer", true);
            System.out.println(FileUtil.getName(file));
        }
        System.out.println(noneUploadedList);
        System.out.println(noneUploadedList.size());
    }

    @Test
    void generateS3Url() {
        File[] files = FileUtil.ls("/Users/breeze/XMUT/BD/backend/IMDB/trailer");
        for (File f : files) {
            if ("mp4".equals(FileUtil.extName(f))){
                QueryWrapper<Info> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("imdb_id", FileUtil.mainName(f));
                Info info = infoService.getOne(queryWrapper);
                S3 s3 = new S3();
                s3.setBucketName("breeze-video-admin");
                s3.setObjectName("trailer/" + FileUtil.getName(f));
                s3.setMovieId(info.getId());
                s3Service.save(s3);
            }
        }
//        System.out.println(FileUtil.getName(files[0]));
    }
}