package com.breeze.breezevideouser.utils;

import lombok.Getter;

/**
 * @author breeze
 * @date 2022/12/12 15:27
 */
@Getter
public class PostgresUtil {
    private final String URL = YamlUtil.getStringByYaml("spring.datasource.url");
    private final String USERNAME = YamlUtil.getStringByYaml("spring.datasource.username");
    private final String PASSWORD = YamlUtil.getStringByYaml("spring.datasource.password");

}
