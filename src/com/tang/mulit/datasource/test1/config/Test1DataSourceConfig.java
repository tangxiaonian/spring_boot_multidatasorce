package com.tang.mulit.datasource.test1.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * @Classname DataSourceConfig
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/12/31 21:02
 * @Created by ASUS
 */
@Configuration
@MapperScan(  // 使用指定的sqlSessionFactory扫描指定的包下面的mapper接口
        value = {"com.tang.mulit.datasource.test1.mapper"},
        sqlSessionFactoryRef = "test1SqlSessionFactory"
)
public class Test1DataSourceConfig {

//    数据源配置 bean 能注入spring 不需要 加 @EnableConfigurationProperties
    @Bean(name = "test01HikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.test01")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    // 配置数据源
    @Bean(name = "test01DataSource")
    public DataSource dataSource(
            @Qualifier(value = "test01HikariConfig") HikariConfig hikariConfig) {

        return new HikariDataSource(hikariConfig);
    }

    // 配置 SqlSessionFactory
    @Bean(name = "test1SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier(value = "test01DataSource")
                                                           DataSource test01DataSource) throws Exception {

        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();

        sessionFactoryBean.setDataSource(test01DataSource);

        // 加载子配置文件
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/test01/*.xml"));

        return sessionFactoryBean.getObject();

    }
    // 配置事务
    @Bean(name = "test01TransactionManager")
    public PlatformTransactionManager platformTransactionManager(
            @Qualifier(value = "test01DataSource") DataSource dataSource) {

        return new DataSourceTransactionManager(dataSource);
    }

    // 配置 SqlSessionTemplate
    @Bean(name = "test01SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier(value = "test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}