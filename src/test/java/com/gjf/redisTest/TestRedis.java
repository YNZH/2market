package com.gjf.redisTest;

import com.gjf.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: GJF
 * @Date : 2018/05/14
 * Time   : 19:29
 */

@RunWith(SpringRunner.class)
//@TestPropertySource(locations = "classpath:config/2market.properties")
@SpringBootTest(classes = Application.class)
public class TestRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test(){
        stringRedisTemplate.opsForValue().set("sessionId","token~~~");
        System.out.println(stringRedisTemplate.opsForValue().get("sessionId"));
    }
}
