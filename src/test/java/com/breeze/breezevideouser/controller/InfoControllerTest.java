package com.breeze.breezevideouser.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.breeze.breezevideouser.domain.Info;
import com.breeze.breezevideouser.service.InfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author breeze
 * @date 2022/12/22 03:06
 */
@SpringBootTest
class InfoControllerTest {

    @Autowired
    InfoService infoService;

    @Test
    void findAll() {
        CsvReader reader = CsvUtil.getReader();
        CsvData data = reader.read(FileUtil.file("/Users/breeze/XMUT/THESIS/backend/breeze-video-user/src/test/java/com/breeze/breezevideouser/controller/links.csv"));
        List<CsvRow> rows = data.getRows();
        int cnt = 0;
        for (CsvRow csvRow : rows) {
//            System.out.println(csvRow.getRawList());
            System.out.println(csvRow.get(0));
            int id = Integer.parseInt(csvRow.get(0));
            Info info = infoService.getById(id);
            if (info!=null){
                System.out.println(info);
                UpdateWrapper<Info> wrapper = new UpdateWrapper<>();
                info.setImdbId(csvRow.get(1));
//                wrapper.set("imdb_id", csvRow.get(1));
                infoService.updateById(info);
            } else {
                continue;
            }
            cnt++;
            System.out.println(cnt);
        }
        System.out.println(cnt);
    }

}