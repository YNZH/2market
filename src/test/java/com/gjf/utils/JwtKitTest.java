package com.gjf.utils;

import com.gjf.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: GJF
 * @Date : 2018/04/23
 * Time   : 9:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class JwtKitTest {
    @Test
    public void testGenerateToken() {
        String id = "1000";
        String token = JwtKit.generateToken("1","高晋峰",50*1000L);
        System.out.println(token);
        System.out.println(JwtKit.parseId(token));
    }
}
