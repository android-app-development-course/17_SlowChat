package com.chat.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HibernateUtil {
    //会话工厂
    private static SessionFactory sessionFactory;

    //读写锁
    private static ReadWriteLock rwlock=new ReentrantReadWriteLock();

    //ThreadLocal类
    private static final ThreadLocal<Session> threadLocal =new ThreadLocal<Session>(){
        @Override
        protected Session initialValue() {
            //获取session可以并行执行，但与重构factory互斥，所以上读锁。
            rwlock.readLock().lock();
            try {
                return sessionFactory.openSession();
            }catch (Exception ex){
                //创建session失败
                ex.printStackTrace();
                return null;
            }finally {
                rwlock.readLock().unlock();
            }
        }

        @Override
        public Session get() {
            Session session=super.get();
            if(!session.isConnected()){
                initialValue();
            }
            return session;
        }

        @Override
        public void remove() {
            Session session=get();
            if(session!=null)session.close();
            set(null);
        }
    };

    static {
        //初始化时创建session工厂
        Configuration cfg=new Configuration().configure("hibernate.cfg.xml");
        sessionFactory=cfg.buildSessionFactory();
    }

    /**
     * 获取传统Session
     * @return 返回Session
     * @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        return threadLocal.get();
    }

    /**
     * 获取hibernate模板
     * @return 返回hibernate模板
     */
    public static HibernateTemplate getTemplate(){
        HibernateTemplate template=new HibernateTemplate(sessionFactory);
        return template;
    }

    /**
     * 获取会话工厂
     * @return 返回会话工厂
     */
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    /**
     * 返回Session
     * @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        threadLocal.remove();
    }

    /**
     * 重建回话工厂
     */
    public static void rebuildSessionFactory(){
        //重构factory与所有相关操作互斥，所以上写锁。
        rwlock.writeLock().lock();
        try {
            Configuration cfg=new Configuration().configure();
            sessionFactory.close();
            sessionFactory=cfg.buildSessionFactory();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            rwlock.writeLock().unlock();
        }
    }
}
