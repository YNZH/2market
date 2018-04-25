package com.gjf.utils;

import com.gjf.mapper.base.BaseMapper;

import java.lang.reflect.Method;
import java.util.Arrays;
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
     * @param mapper     被@Mapper注解的mapper接口
     * @param modelClazz model对应的Class
     * @param entities   实例化的model  至少传入两个（id必须为主键字段不同）
     * @param <M>        model类
     */
    public static <M> void execute(BaseMapper<M> mapper, Class<?> modelClazz, M... entities) {
        if (entities.length < 2) {
            System.out.println("实体个数应至少为2");
        }
        System.out.println("=========================增（" + entities.length + "条）===============");
        for (M entity : entities
                ) {
            mapper.insert(entity);
        }
        System.out.println("增加的记录:" + printEntityBySelectAll(mapper));

        System.out.println("=========================删第一个实体===========================");
        Long id = 0L;
        try {
            Method getId = modelClazz.getDeclaredMethod("getId");
            Method[] methods = modelClazz.getDeclaredMethods();
            Method setName = Arrays.stream(methods).
                    filter(x -> x.getName().contains("name")).
                    findFirst().get();
            id = (Long) getId.invoke(entities[0]);
            System.out.println("id===" + id);
            mapper.deleteByPrimaryKey(id);
            System.out.println("删除之后记录：" + printEntityBySelectAll(mapper));

            System.out.println("=========================更新第2个实体===========================");
            setName.invoke(entities[1], "用于测试更新的名字~");
            mapper.updateByPrimaryKey(entities[1]);
            System.out.println("更新之后记录：" + printEntityBySelectAll(mapper));
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
