package com.cny.mybatissource.plugin;

import com.cny.mybatissource.annotation.NeedInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.javassist.tools.reflect.Metaobject;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * Mybatis 实现自定义插件
 *
 * @author : chennengyuan
 */
@Slf4j
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class}
        )
})
public class MyBatisInteceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Exception {
        log.info("进入了 MyBatisInteceptor 自定义插件");
        try {
            // 获取sql信息
            StatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
            BoundSql boundSql = statementHandler.getBoundSql();
            String sql = boundSql.getSql();
            log.info("获取到sql为：{}", sql);

            // 获取MappedStatement对象
            MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

            //获取到类名、方法名
            String id = mappedStatement.getId();
            String className = id.substring(0, id.lastIndexOf("."));
            String methodName = id.substring(id.lastIndexOf(".") + 1);
            log.info("获取到className为：{}，methodName为：{}", className, methodName);

            //若方法加了注解，则修改sql表名称
            Class<?> clazz = Class.forName(className);
            Method method = clazz.getDeclaredMethod(methodName, boundSql.getParameterObject().getClass());
            if (method.isAnnotationPresent(NeedInterceptor.class)) {
                NeedInterceptor annotation = method.getAnnotation(NeedInterceptor.class);
                String originalTableName = annotation.originalTableName();
                String newTableName = annotation.newTableName();
                String newsql = sql.replace(originalTableName, newTableName);

                //修改sql语句
                Class<? extends BoundSql> boundClazz = boundSql.getClass();
                Field field = boundClazz.getDeclaredField("sql");
                field.setAccessible(true);
                field.set(boundSql, newsql);
            }
            return invocation.proceed();
        } catch (Exception e) {
            throw new Exception("拦截Sql替换表名称出现异常：" + e.getMessage());
        }


    }
}
