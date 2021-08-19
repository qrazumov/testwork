package utils;

import java.io.IOException;
import java.io.Reader;

import com.typesafe.config.Config;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;


public class MyBatisConfig {

    @Inject
    private Config config;
    private SqlSession sqlSession;
    private SqlSessionFactory sqlSessionFactory;

    public SqlSession sqlSession() {

        String mybatisConfigPath = config.getString("mybatisConfigPath");

        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(mybatisConfigPath);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
            sqlSession = factory.openSession();
        } catch (IOException e) {
            // TODO add log
            e.printStackTrace();
        }

        return sqlSession;

    }

    public SqlSessionFactory sqlSessionFactory(){
        String mybatisConfigPath = config.getString("mybatisConfigPath");
        Reader reader = null;
        SqlSessionFactory factory = null;
        try {
            reader = Resources.getResourceAsReader(mybatisConfigPath);
            factory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            // TODO add log
            e.printStackTrace();
        }

        return factory;
    }

}
