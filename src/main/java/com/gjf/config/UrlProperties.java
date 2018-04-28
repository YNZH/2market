package com.gjf.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 此属性配置的url 将不会经过LoginRequiredFilter过滤器
 *
 * @Author: GJF
 * @Date : 2018/04/27
 * Time   : 0:08
 */
@Component
@PropertySource("classpath:/config/2market.properties")
@ConfigurationProperties(prefix = "url")
public class UrlProperties {
    @Getter
    @Setter
    private String excluded;
    @Getter
    @Setter
    private String separator;
}
