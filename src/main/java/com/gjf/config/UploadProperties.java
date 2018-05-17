package com.gjf.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: GJF
 * @Date : 2018/04/27
 * Time   : 10:29
 */
@Component
@PropertySource("classpath:config/2market.properties")
@ConfigurationProperties(prefix = "upload")
public class UploadProperties {

    @Getter @Setter private String goodsImgFolder;

    @Getter @Setter private String headerImgFolder;

    @Getter @Setter private String fileFolder;
    @Getter @Setter private String prefix;
}
