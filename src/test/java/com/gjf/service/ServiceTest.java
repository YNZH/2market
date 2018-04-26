package com.gjf.service;

import com.gjf.mapper.CommentMapper;
import com.gjf.mapper.GoodsMapper;
import com.gjf.mapper.UserMapper;
import com.gjf.mapper.base.BaseMapper;
import com.gjf.model.Comment;
import com.gjf.model.Goods;
import com.gjf.model.User;
import com.gjf.utils.CURDTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: GJF
 * @Date : 2018/04/22
 * Time   : 21:28
 */
@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ServiceTest {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void CURDTemplateTest(){
        User user1 = new User(1L, "一号", "1231", "2313", "12313", "FEMALE", "wuhanlig ", "渔区", "33123", "headImg", Byte.valueOf("1"), new Date(), null);
        User user2 = new User(2L, "二号", "1231", "2313", "12313", "MALE", "wuhanlig ", "渔区", "33123", "headImg", Byte.valueOf("1"), new Date(), null);
        CURDTemplate.execute(userMapper,user1,user2);

        Goods goods1 = new Goods(1L, 1L, "商品1", "link链接1", BigDecimal.valueOf(100.2), "武汉理工", "渔区",
                new Date(), null);
        Goods goods2 = new Goods(2L, 1L, "商品2", "link链接2", BigDecimal.valueOf(100.2), "武汉理工", "渔区",
                new Date(), null);
        CURDTemplate.execute(goodsMapper,goods1,goods2);

        Comment comment1 = new Comment(1L,1L,1L,"这是评论1","图片1",new Date(),null);
        Comment comment2 = new Comment(2L,2L,2L,"这是评论2","图片2",new Date(),null);
        CURDTemplate.execute(commentMapper,comment1,comment2);
    }

    @Test
    public void testUserService() {
        //增删改查
        User user1 = new User(1L, "一号", "1231", "2313", "12313", "FEMALE", "wuhanlig ", "渔区", "33123", "headImg", Byte.valueOf("1"), new Date(), null);
        User user2 = new User(2L, "二号", "1231", "2313", "12313", "MALE", "wuhanlig ", "渔区", "33123", "headImg", Byte.valueOf("1"), new Date(), null);
        userMapper.insert(user1);
        userMapper.insert(user2);
        System.out.println(userMapper.selectAll());
        userMapper.deleteByPrimaryKey(user1.getId());
        System.out.println(userMapper.selectAll());
        user2.setNickname("更新后的二号");
        userMapper.updateByPrimaryKey(user2);
        System.out.println(userMapper.selectByPrimaryKey(user2.getId()));
    }

    @Test
    public void testGoodsService() {
        //增删改查

        Goods goods = new Goods(1L, 1L, "商品1", "link链接", BigDecimal.valueOf(100.2), "武汉理工", "渔区",
                new Date(), null);
        Goods goods1 = new Goods(2L, 1L, "商品2", "link链接", BigDecimal.valueOf(100.2), "武汉理工", "渔区",
                new Date(), null);

        goodsMapper.insert(goods);
        goodsMapper.insert(goods1);
        System.out.println("增===========>：" + goodsMapper.selectAll().toString());

        goodsMapper.deleteByPrimaryKey(1L);
        System.out.println("删==========>：" + goodsMapper.selectAll().toString());

        goods1.setName("更新姓名");
        goodsMapper.updateByPrimaryKey(goods1);
        System.out.println("改======>：" + goodsMapper.selectAll().toString());

        System.out.println("查======》" + goodsMapper.selectByPrimaryKey(2L).toString());

    }


    @Test
    public void testCommentService() {
        //增删改查
    }
}
