package com.chat.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {
    private static final String[] PATH={
            "mvc-dispatcher.xml"
    };
    private static ApplicationContext context=
            new ClassPathXmlApplicationContext(PATH);

    public static ApplicationContext getApplicationContext(){
        return context;
    }

    public static <T> T getBean(Class<T> t){
        return context.getBean(t);
    }

    public static <T> T getBean(String id,Class<T> t){
        return context.getBean(id,t);
    }
}
