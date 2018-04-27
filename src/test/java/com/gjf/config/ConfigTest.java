package com.gjf.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author: GJF
 * @Date : 2018/04/27
 * Time   : 0:37
 */
@RunWith(SpringRunner.class)
@EnableConfigurationProperties({UrlConfig.class,UploadConfig.class})
public class ConfigTest {
    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    private UploadConfig uploadConfig;

    @Test
    public void test(){
        String excludedUrls = urlConfig.getExcluded();
        System.out.println(Arrays.stream(excludedUrls.split(",")).collect(
                Collectors.joining("\n")));

        System.out.println(uploadConfig.getFileFolder());
        System.out.println(uploadConfig.getGoodsImgFolder());
    }
}
