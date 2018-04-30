package com.gjf.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @Author: GJF
 * @Date : 2018/04/30
 * Time   : 10:33
 */
@Component
@PropertySource(value = "classpath:/config/2market.properties",encoding = "UTF-8")
@ConfigurationProperties(prefix = "schools")
public class SchoolProperties {

    /**
     * @ Map<String,String> 1th String represent School's name
     *                      2th String represent School's URL and ClassName ,whose form as {http://school.com,package.school.WHUT}
     */
    @Setter @Getter private HashMap<String,String> map = new HashMap<>(2);
}
