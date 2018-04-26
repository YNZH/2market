package com.gjf.utils;

import com.gjf.mapper.base.BaseMapper;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: GJF
 * @Date : 2018/04/25
 * Time   : 17:24
 */
public class CURDTemplate {

    /**
     * 测试 Mybatis generator 生成的CURD的模板方法 model必须有主键 同时必须命名为id
     *
     * @param mapper   被@Mapper注解的mapper接口
     * @param entities 实例化的model  至少传入两个（id必须为主键字段不同）
     * @param <M>      model类
     */
    public static <M> void execute(BaseMapper<M> mapper, M... entities) {
        if (entities.length < 2) {
            System.out.println("实体个数应至少为2");
            return;
        }
        Class<?> modelClazz = entities[0].getClass();
        String entityName = entities[0].getClass().getName();
        System.out.println("=========================增（" + entities.length + "条" + entityName + "记录）===============");
        for (M entity : entities
                ) {
            mapper.insert(entity);
        }
        System.out.println("增加" + entityName + "之后的记录:" + printEntityBySelectAll(mapper));

        System.out.println("=========================删第一个" + entityName + "===========================");
        Long id = 0L;
        try {
            Method getId = modelClazz.getDeclaredMethod("getId");
            Method setTimeModified = Arrays.stream(modelClazz.getDeclaredMethods()).
                    filter(x -> x.getName().contains("setTimeModified")).
                    findFirst().get();
            id = (Long) getId.invoke(entities[0]);
            System.out.println("id===" + id);
            mapper.deleteByPrimaryKey(id);
            System.out.println("删除第一个" + entityName + "记录：" + printEntityBySelectAll(mapper));

            System.out.println("=========================更新第2个" + entityName + "的字段（timeModified）更新时间===========================");
            setTimeModified.invoke(entities[1], new Date());
            mapper.updateByPrimaryKey(entities[1]);
            System.out.println("更新第2个" + entityName + "之后记录(更新字段为timeModified)：" + printEntityBySelectAll(mapper));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param mapper mybatis3 @Mapper注解的接口
     * @param <M>    model （实现toString()方法)
     * @return 通过mybatis generator 自动生成的接口方法之一的 selectAll()查询到的List<M>.
     */
    private static <M> String printEntityBySelectAll(BaseMapper<M> mapper) {
        return mapper.selectAll()
                .stream()
                .map(Objects::toString)
                .collect(Collectors.joining("\n"));
    }


}
