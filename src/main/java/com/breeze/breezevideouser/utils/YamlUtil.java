package com.breeze.breezevideouser.utils;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

/**
 * @author breeze
 * @date 2022/12/12 15:21
 */
public class YamlUtil {
    public static String getStringByYaml (String key) {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        Properties properties = yaml.getObject();
        assert properties != null;
        return (String) properties.get(key);
    }
}
