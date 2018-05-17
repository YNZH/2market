package com.gjf.service;

import com.gjf.mapper.*;
import com.gjf.model.*;
import com.gjf.utils.CURDTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: GJF
 * @Date : 2018/04/22
 * Time   : 21:28
 */
@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CURDTest {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private CategoryMapper categoryMapper;


    @Test public void testSchoolMapper(){
        School school1 = new School(100L,"大学一","校区1");
        School school2 = new School(101L,"大学二","校区2");
        CURDTemplate.execute(schoolMapper,school1,school2);
    }

    @Test public void testCategoryMapper(){
        Category category1 = new Category(101L,"src1","分类1");
        Category category2 = new Category(102L,"src2","分类2");
        CURDTemplate.execute(categoryMapper,category1,category2);
    }

    @Test public void testUserService() {
        //增删改查
        User user1 = new User(1L, "一号", "1231", "2313", "12313", "FEMALE", "wuhanlig ", "渔区", "33123", "headImg", Byte.valueOf("1"), new Date(), null);
        User user2 = new User(2L, "二号", "1231", "2313", "12313", "MALE", "wuhanlig ", "渔区", "33123", "headImg", Byte.valueOf("1"), new Date(), null);
        CURDTemplate.execute(userMapper,user1,user2);
    }

    @Test public void testGoodsService() {

        Goods goods1 = new Goods(1L, 1L, "商品1", "link链接1", BigDecimal.valueOf(100.2),"最新发布","描述1", "武汉理工", "渔区",
                new Date(), null);
        Goods goods2 = new Goods(2L, 1L, "商品2", "link链接2", BigDecimal.valueOf(100.2),"闲置数码","描述2", "武汉理工", "渔区",
                new Date(), null);
        Goods goods3 = new Goods(3L, 1L, "商品2", "link链接2", BigDecimal.valueOf(100.2), "校园代步","描述3","武汉理工", "渔区",
                new Date(), null);
//
        // BaseMapper增删改查
       // CURDTemplate.execute(goodsMapper,goods1,goods2);

        goodsMapper.insert(goods1);
        goodsMapper.insert(goods2);
        goodsMapper.insert(goods3);
        System.out.println(goodsMapper.getGoodsCountByUserId(1L));
        goods3.setUserId(2L);
        goodsMapper.updateByPrimaryKey(goods3);
        System.out.println(goodsMapper.getGoodsCountByUserId(1L));

    }

    @Test public void testCommentService() {
        //增删改查
        Comment comment1 = new Comment(1L,1L,1L,1L,"这是评论1","图片1",new Date(),null);
        Comment comment2 = new Comment(2L,2L,2L,1L,"这是评论2","图片2",new Date(),null);
        CURDTemplate.execute(commentMapper,comment1,comment2);
    }
}
