package com.gmcc.yzcardmessage.util;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;


/**
 * 主数据源
 */
@Configuration
@MapperScan(basePackages = "com.gmcc.yzcardmessage.dao.master", sqlSessionFactoryRef = "masterSqlSessionFactory")
public class DataSourceConfig {

    // 将这个对象放入Spring容器中
    @Bean(name = "masterDataSource")
    // 表示这个数据源是默认数据源
    @Primary
    // 读取application.properties中的配置参数映射成为一个对象
    // prefix表示参数的前缀
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource getDateSource1()
    {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "masterSqlSessionFactory")
    // 表示这个数据源是默认数据源
    @Primary
    // @Qualifier表示查找Spring容器中名字为test1DataSource的对象
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("masterDataSource") DataSource datasource)
            throws Exception
    {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:/mybatis-mapper/master/*Dao.xml"));
        return bean.getObject();
    }


    @Bean("masterSqlSessionTemplate")
    // 表示这个数据源是默认数据源
    @Primary
    public SqlSessionTemplate test1SqlSessionTemplate(
            @Qualifier("masterSqlSessionFactory") SqlSessionFactory sessionFactory)
    {
        return new SqlSessionTemplate(sessionFactory);
    }

    @Primary
    public SqlSessionTemplate test2SqlSessionTemplate(
            @Qualifier("masterSqlSessionFactory") SqlSessionFactory sessionFactory){
        return new SqlSessionTemplate(sessionFactory);
    }




}
