package com.gjf.service;

import com.gjf.Application;
import com.gjf.mapper.GoodsMapper;
import com.gjf.model.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: GJF
 * @Date : 2018/04/22
 * Time   : 21:28
 */
@RunWith(SpringRunner.class)

@Transactional
public class ServiceTest {

    @Autowired
    private GoodsMapper goodsMapper;

    public ServiceTest() {
    }

    @Test
    @Rollback
    public void testUserService(){
        //增删改查

    }

    @Test
    @Rollback
    public void testGoodsService(){
        //增删改查
        Goods goods = new Goods(1L,1L,"商品1", "link链接",BigDecimal.valueOf(100.2),"武汉理工","渔区",
                new Date(),null);
        Goods goods1 = new Goods(2L,1L,"商品2", "link链接",BigDecimal.valueOf(100.2),"武汉理工","渔区",
                new Date(),null);

        goodsMapper.insert(goods);
        goodsMapper.insert(goods1);

        goodsMapper.deleteByPrimaryKey(1L);

        goods1.setName("id：2 更新");
        goodsMapper.updateByPrimaryKey(goods1);
        goodsMapper.selectByPrimaryKey(2L);
    }

    @Test
    @Rollback
    public void testCommentService(){
        //增删改查
    }
}
