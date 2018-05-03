package com.gjf.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: GJF
 * @Date : 2018/04/28
 * Time   : 15:42
 */
@Component
@PropertySource(value = "classpath:/config/2market.properties")
@ConfigurationProperties(prefix = "token")
public class TokenProperties {
    @Getter @Setter private String secret;
    @Getter @Setter private Long expireTime;
}
