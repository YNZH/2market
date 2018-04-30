package com.gjf.service;

import com.gjf.config.SchoolProperties;
import com.gjf.validator.certification.school.BaseSchool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: GJF
 * @Date : 2018/04/30
 * Time   : 11:21
 */
@Component
public class SchoolFactory {
    private static final String SEPARATOR = ",";

    private static SchoolProperties schoolProperties;

    @Autowired
    public SchoolFactory(SchoolProperties schoolProperties) {
        SchoolFactory.schoolProperties = schoolProperties;
    }

    public static BaseSchool getInstance(String schoolName){
        BaseSchool school = null;
        String[] urlAndClassName = schoolProperties.getMap().get(schoolName).split(SEPARATOR);
        try {
            Constructor constructor = Class.forName(urlAndClassName[1]).getConstructor(String.class);
           school = (BaseSchool) constructor.newInstance(urlAndClassName[0]);
        } catch (NoSuchMethodException | InvocationTargetException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return school;
    }
}
