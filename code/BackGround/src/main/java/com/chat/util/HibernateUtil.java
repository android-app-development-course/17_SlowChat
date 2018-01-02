package com.chat.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HibernateUtil {
    //会话工厂
    private static SessionFactory sessionFactory;


    static {
        //初始化时创建session工厂
        Configuration cfg=new Configuration().configure("hibernate.cfg.xml");
        sessionFactory=cfg.buildSessionFactory();
    }

    /**
     * 获取绑定在线程上的session
     * @return 返回Session
     * @throws HibernateException
     */
    public static Session getCurrentSession() throws HibernateException {
        return sessionFactory.getCurrentSession();
    }

    public static Session openSession(){
        return sessionFactory.openSession();
    }


    /**
     * 获取会话工厂
     * @return 返回会话工厂
     */
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
