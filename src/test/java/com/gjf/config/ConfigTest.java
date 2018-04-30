package com.gjf.config;

import com.gjf.Application;
import com.gjf.service.SchoolFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author: GJF
 * @Date : 2018/04/27
 * Time   : 0:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
//@EnableConfigurationProperties({UrlProperties.class, UploadProperties.class,SchoolProperties.class})
public class ConfigTest {
    @Autowired
    private UrlProperties urlProperties;
    @Autowired
    private UploadProperties uploadProperties;

    @Test
    public void test(){
        String excludedUrls = urlProperties.getExcluded();
        System.out.println(Arrays.stream(excludedUrls.split(urlProperties.getSeparator())).collect(
                Collectors.joining("\n")));

        System.out.println(uploadProperties.getFileFolder());
        System.out.println(uploadProperties.getGoodsImgFolder());

//        System.out.println(schoolProperties.getMap());
//        String[] urlAndCname = schoolProperties.getMap().get("武汉理工大学").split(",");
//        try {
//            Class schoolClazz = Class.forName(urlAndCname[1]);
//            BaseSchool school = (BaseSchool) schoolClazz.getConstructor(String.class).newInstance(urlAndCname[0]);
//
//            System.out.println(school.simulateLogin("0121410870525","030157"));
//        } catch (ClassNotFoundException| InvocationTargetException |NoSuchMethodException | InstantiationException |IllegalAccessException e) {
//            e.printStackTrace();
//        } finally {
//        }
//        System.out.println(schoolFactory);
        System.out.println(SchoolFactory.getInstance("武汉理工大学").simulateLogin("0121410870525","030157"));

    }
}
