package com.cts.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = { "com.cts.services" })
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.cts.repo" })
public class JpaConfig {

	private static final Logger logger = LoggerFactory.getLogger(JpaConfig.class);

	@Autowired
	Environment env;

	@Bean
	public DataSource dataSource() {
		logger.info("Preparing the DataSource");
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("driverClassName"));
		dataSource.setUrl(env.getProperty("url"));
		dataSource.setUsername(env.getProperty("user"));
		dataSource.setPassword(env.getProperty("password"));
		dataSource.setInitialSize(10);
		logger.info("{}", dataSource);
		return dataSource;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan("com.cts.entities");
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.setJpaProperties(jpaProperties());
		factoryBean.afterPropertiesSet();
		return factoryBean.getNativeEntityManagerFactory();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(entityManagerFactory());
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	@Bean
	public Properties jpaProperties() {
		Properties props = new Properties();
		props.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		props.put("hibernate.hbm2ddl.auto", "update");
		return props;
	}

}
