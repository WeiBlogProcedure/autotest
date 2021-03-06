package com.course.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @ClassName DateBaseUtil
 * @Description TODO
 * @Author lixinwei
 * @Date 2021/8/24 11:28 下午
 * @Version 1.0
 **/
public class DatabaseUtil {
    public static SqlSession getSqlSession() throws IOException {
        //获取配置的资源文件
        Reader reader = Resources.getResourceAsReader("databaseConfig.xml");
        //加载配置文件
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        //返回sqlSession，它能够执行配置文件中的sql语句
        SqlSession sqlSession = factory.openSession();
        return sqlSession;
    }
}
