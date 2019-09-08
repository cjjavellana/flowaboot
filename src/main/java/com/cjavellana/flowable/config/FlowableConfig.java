package com.cjavellana.flowable.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.ldap.LDAPConfiguration;
import org.flowable.ldap.LDAPConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlowableConfig {

    @Bean
    public ProcessEngineConfiguration processEngine(DataSource dataSource) {
        ProcessEngineConfiguration config = new StandaloneProcessEngineConfiguration();
        config.setDataSource(dataSource);
        config.setDatabaseType("mysql");
        config.setDatabaseSchemaUpdate("true");
        config.setMailServerHost("localhost");
        config.setMailServerPort(1025);

        LDAPConfiguration ldapConfiguration = new LDAPConfiguration();
        ldapConfiguration.setServer("ldap://localhost");
        ldapConfiguration.setPort(10389);
        ldapConfiguration.setUser("uid=christian, ou=users, o=flowable");
        ldapConfiguration.setPassword("test");

        LDAPConfigurator ldapConfigurator = new LDAPConfigurator();
        ldapConfigurator.setLdapConfiguration(ldapConfiguration);

        config.setIdmEngineConfigurator(ldapConfigurator);

        return config;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnCreate(true);
        dataSource.setTestOnReturn(true);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/workflow");
        dataSource.setMinIdle(10);
        dataSource.setMaxTotal(80);
        return dataSource;
    }
}
