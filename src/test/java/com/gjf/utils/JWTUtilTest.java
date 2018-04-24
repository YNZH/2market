package com.gjf.utils;

import io.jsonwebtoken.Jwts;
import org.junit.Test;

/**
 * @Author: GJF
 * @Date : 2018/04/23
 * Time   : 9:50
 */
public class JWTUtilTest {
    @Test
    public void testGenerateToken() {
        String id = "1000";
        
        String token = JWTUtil.generateToken("1","高晋峰",5*1000L,"ofbafgfbb");
        System.out.println(token);
        System.out.println(JWTUtil.parseId(token,"ofbafgfbb"));
    }
}
