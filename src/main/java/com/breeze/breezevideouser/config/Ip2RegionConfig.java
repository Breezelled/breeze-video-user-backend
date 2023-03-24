package com.breeze.breezevideouser.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.breeze.breezevideouser.utils.IPUtil;
import org.apache.commons.io.FileUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author breeze
 * @date 2023/3/24 09:59
 */
@Configuration
public class Ip2RegionConfig {

    static final Logger logger = LoggerFactory.getLogger(Ip2RegionConfig.class);

    @Bean
    public static Searcher searcher() throws IOException {
        //获得文件流时，因为读取的文件是在打好jar文件里面，不能直接通过文件资源路径拿到文件，但是可以在jar包中拿到文件流
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("ip2region.xdb");
        Resource resource = resources[0];
        InputStream is = resource.getInputStream();
        File target = new File("ip2region.xdb");
        FileUtils.copyInputStreamToFile(is, target);
        is.close();
        if (StringUtils.isEmpty(String.valueOf(target))) {
            logger.error("Error: Invalid ip2region.xdb file");
        }

        String dbPath = String.valueOf(target);

        // 1、从 dbPath 加载整个 xdb 到内存。
        byte[] cBuff = Searcher.loadContentFromFile(dbPath);

        // 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。

        return Searcher.newWithBuffer(cBuff);
    }
}
