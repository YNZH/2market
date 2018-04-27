package com.gjf.utils;

import com.gjf.mapper.base.BaseMapper;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
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
            return ;
        }
        Class<?> modelClazz = entities[0].getClass();
        String entityName = entities[0].getClass().getName();
        System.out.println("=========================增（" + entities.length + "条" + entityName + "记录）===============");
        for (M entity : entities
                ) {
            mapper.insert(entity);
        }
        System.out.println("增加" + entityName + "之后的记录:\n" + printEntityBySelectAll(mapper));

        System.out.println("=========================删第一个" + entityName + "===========================");
        Long id;
        try {
            Method getId = modelClazz.getDeclaredMethod("getId");
            id = (Long) getId.invoke(entities[0]);
            mapper.deleteByPrimaryKey(id);
            System.out.println("删除第一个" + entityName + "记录后：\n" + printEntityBySelectAll(mapper));


            Method setTimeModified;
            Optional<Method> optional = Arrays.stream(modelClazz.getDeclaredMethods()).
                    filter(x -> x.getName().contains("setTimeModified")).
                    findFirst();
            if (optional.isPresent()) {
                setTimeModified = optional.get();
                System.out.println("=========================更新第2个" + entityName + "的字段（timeModified）更新时间===========================");
                setTimeModified.invoke(entities[1], new Date());
                mapper.updateByPrimaryKey(entities[1]);
                System.out.println("更新第2个" + entityName + "之后记录(更新字段为timeModified)：\n" + printEntityBySelectAll(mapper));
                return;
            }

            Method setName;
            optional = Arrays.stream(modelClazz.getDeclaredMethods()).
                    filter(x -> x.getName().contains("setName")).
                    findFirst();
            if (optional.isPresent()) {
                setName = optional.get();
                System.out.println("=========================更新第2个" + entityName + "的字段（name）===========================");
                setName.invoke(entities[1], "用于测试更新的名字~");
                mapper.updateByPrimaryKey(entities[1]);
                System.out.println("更新第2个" + entityName + "之后记录(更新字段为name)：\n" + printEntityBySelectAll(mapper));
                return;
            }
            else {
                System.out.println("请为实体添加 id 或者 timeModified 字段");
            }
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
