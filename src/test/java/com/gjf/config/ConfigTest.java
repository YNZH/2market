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
@EnableConfigurationProperties({UrlProperties.class, UploadProperties.class})
public class ConfigTest {
    @Autowired
    private UrlProperties urlProperties;
    @Autowired
    private UploadProperties uploadProperties;

    @Test
    public void test(){
        String excludedUrls = urlProperties.getExcluded();
        System.out.println(Arrays.stream(excludedUrls.split(",")).collect(
                Collectors.joining("\n")));

        System.out.println(uploadProperties.getFileFolder());
        System.out.println(uploadProperties.getGoodsImgFolder());
    }
}
