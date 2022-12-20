package com.breeze.breezevideouser;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.querys.PostgreSqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.baomidou.mybatisplus.generator.keywords.PostgreSqlKeyWordsHandler;
import com.breeze.breezevideouser.utils.PostgresUtil;

import java.util.Collections;

/**
 * @author breeze
 * @date 2022/12/12 15:08
 */
public class Generator {
    static PostgresUtil postgresUtil = new PostgresUtil();
    private static final DataSourceConfig.Builder DATA_CONFIG_BUILDER=
            new DataSourceConfig.Builder(postgresUtil.getURL(), postgresUtil.getUSERNAME(), postgresUtil.getPASSWORD())
                    .dbQuery(new PostgreSqlQuery())
                    .schema("public")
                    .typeConvert(new PostgreSqlTypeConvert())
                    .keyWordsHandler(new PostgreSqlKeyWordsHandler());

    public static void main(String[] args) {
        String pkgPath = System.getProperty("user.dir") + "/src/main/java";
        String resPath = System.getProperty("user.dir") + "/src/main/resources";
        FastAutoGenerator.create(DATA_CONFIG_BUILDER)
                .globalConfig(builder -> {
                    builder.author("breeze") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(pkgPath) // 指定输出目录
                            .disableOpenDir(); //禁用打开文件夹
                })
                .packageConfig(builder -> {
                    builder.parent("com.breeze.breezevideouser") // 设置父包名
                            .entity("DO") // 实体输出到DO
                            .pathInfo(Collections.singletonMap(OutputFile.xml, resPath + "/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addExclude() // 设置排除生成的表
                            .mapperBuilder().enableBaseColumnList().enableBaseResultMap()
                            .serviceBuilder().formatServiceFileName("%sService")
                            .controllerBuilder().enableRestStyle()
                            .entityBuilder().enableChainModel().columnNaming(NamingStrategy.underline_to_camel).naming(NamingStrategy.underline_to_camel)
                            .enableLombok();
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
