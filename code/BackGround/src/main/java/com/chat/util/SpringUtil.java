package com.chat.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {
    private static final String[] PATH={
            "dao-application.xml"
    };
    private static ApplicationContext context=
            new ClassPathXmlApplicationContext(PATH);

    public static ApplicationContext getApplicationContext(){
        return context;
    }

    public static <T> T getBean(Class<T> t){
        return context.getBean(t);
    }
}
