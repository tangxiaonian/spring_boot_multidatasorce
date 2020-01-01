package com.tang.mulit.datasource.test1.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
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
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.sql.SQLException;

/**
 * @Classname Test1AtomikosDataSourceConfig
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/1/1 21:28
 * @Created by ASUS
 */
@Configuration
@MapperScan(  // 使用指定的sqlSessionFactory扫描指定的包下面的mapper接口
        value = {"com.tang.mulit.datasource.test1.mapper"},
        sqlSessionFactoryRef = "test1SqlSessionFactory"
)
public class Test1AtomikosDataSourceConfig {

    //    数据源配置 bean 能注入spring 不需要 加 @EnableConfigurationProperties
    @Bean(name = "test01HikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.test01")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    // 配置数据源
    @Bean(name = "test01DataSource")
    public DataSource dataSource(
            @Qualifier(value = "test01HikariConfig") HikariConfig hikariConfig) throws SQLException {

        // 针对MySQL的xa
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();

        mysqlXaDataSource.setUrl(hikariConfig.getJdbcUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(hikariConfig.getPassword());
        mysqlXaDataSource.setUser(hikariConfig.getUsername());

        // 配置 AtomikosDataSourceBean
        AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();

        dataSourceBean.setXaDataSource(mysqlXaDataSource);
        dataSourceBean.setUniqueResourceName("xads1");
        dataSourceBean.setBorrowConnectionTimeout(60);
        dataSourceBean.setMaxPoolSize(20);

        return dataSourceBean;
    }

    @Bean(name = "userTransaction")
    public UserTransaction userTransaction() throws Throwable {

        UserTransactionImp userTransactionImp = new UserTransactionImp();

        userTransactionImp.setTransactionTimeout(10000);

        return userTransactionImp;
    }

    // 事务管理器
    @Bean(name = "atomikosTransactionManager")
    public TransactionManager atomikosTransactionManager() {

        UserTransactionManager userTransactionManager = new UserTransactionManager();

        userTransactionManager.setForceShutdown(true);

        return userTransactionManager;
    }

//    事务管理器  事务配置一次
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(UserTransaction userTransaction,
                                                         TransactionManager atomikosTransactionManager) {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager(userTransaction, atomikosTransactionManager);

        jtaTransactionManager.setAllowCustomIsolationLevels(true);

        return jtaTransactionManager;
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

    // 配置 SqlSessionTemplate
    @Bean(name = "test01SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier(value = "test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}