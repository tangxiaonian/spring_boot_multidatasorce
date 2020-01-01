package com.tang.mulit.datasource.test2.config;

import com.mysql.cj.jdbc.MysqlXADataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Classname DataSourceConfig
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/12/31 21:02
 * @Created by ASUS
 */
@Configuration
@MapperScan(
        value = {"com.tang.mulit.datasource.test2.mapper"},
        sqlSessionFactoryRef = "test02SqlSessionFactory"
)
public class Test2AtomikosDataSourceConfig {

    @Bean(name = "test02HikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.test02")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean(name = "test02DataSource")
    public DataSource dataSource(
            @Qualifier(value = "test02HikariConfig") HikariConfig hikariConfig) throws SQLException {

        // 针对MySQL的xa
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();

        mysqlXaDataSource.setUrl(hikariConfig.getJdbcUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(hikariConfig.getPassword());
        mysqlXaDataSource.setUser(hikariConfig.getUsername());

        // 配置 AtomikosDataSourceBean
        AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();

        dataSourceBean.setXaDataSource(mysqlXaDataSource);
        dataSourceBean.setUniqueResourceName("xads2");
        dataSourceBean.setBorrowConnectionTimeout(60);

        return dataSourceBean;
    }

    @Bean(name = "test02SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier(value = "test02DataSource")
                                                       DataSource test02DataSource) throws Exception {

        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();

        sessionFactoryBean.setDataSource(test02DataSource);

        // 加载子配置文件
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/test02/*.xml"));

        return sessionFactoryBean.getObject();

    }

    @Bean(name = "test02SqlSessionFactory")
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier(value = "test02SqlSessionFactory") SqlSessionFactory test02SqlSessionFactory) {
        return new SqlSessionTemplate(test02SqlSessionFactory);
    }

}