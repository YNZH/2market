package com.gjf;

import com.gjf.model.Goods;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: GJF
 * @Date : 2018/04/19
 * Time   : 14:24
 */
public class LombokTest {

    @Test
    public void testLombok(){
        Goods goods = new Goods();
        Goods goods1 = new Goods(2L,1L,"商品2", "link链接", BigDecimal.valueOf(100.2),"武汉理工","渔区",
                new Date(),new Date());
        goods.setName("测试");
        System.out.println(goods);
        System.out.println(goods.toString());
        System.out.println(goods1);
        System.out.println(goods1.toString());
    }
}
