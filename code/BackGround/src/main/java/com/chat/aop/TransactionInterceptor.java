package com.chat.aop;


import com.chat.util.HibernateUtil;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 事务切面，把Hibernate的事务开启和事务结束切入到控制器的最上端和最下端
 */
@Aspect
@Component
public class TransactionInterceptor {
    @Pointcut("execution(public * com.chat.controller..*.*(..))")
    public void pointcut(){

    }

    @Before("pointcut()")
    public void beginTransaction(){
        HibernateUtil.getCurrentSession().beginTransaction();
    }

    @After("pointcut()")
    public void commitTransaction(){
        HibernateUtil.getCurrentSession().getTransaction().commit();
    }
}
