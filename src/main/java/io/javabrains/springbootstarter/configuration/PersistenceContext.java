package io.javabrains.springbootstarter.configuration;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"io.javabrains.springbootstarter"},
		entityManagerFactoryRef = "entityManagerFactory",
		transactionManagerRef = "transactionManager")
@EnableTransactionManagement
public class PersistenceContext {
	
	@Autowired
	private Environment env;
	
	@Value("${datasource.sampleapp.maxPoolSize:10}")
	private int maxPoolSize;
	
	private Properties jpaProperties(){
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", env.getRequiredProperty("datasource.sampleapp.hibernate.dialect"));
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("datasource.sampleapp.hibernate.hbm2ddl.auto"));
        jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("datasource.sampleapp.hibernate.show_sql"));
        jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("datasource.sampleapp.hibernate.format_sql"));
        if(StringUtils.isNotEmpty(env.getRequiredProperty("datasource.sampleapp.defaultSchema"))){
            jpaProperties.put("hibernate.default_schema", env.getRequiredProperty("datasource.sampleapp.defaultSchema"));
        }
        return jpaProperties;
	}
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "datasource.sampleapp")
	public DataSourceProperties dataSourceProperties(){
		return new DataSourceProperties();
	}
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource(){
		DataSourceProperties dataSourceProperties = dataSourceProperties();
		HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
				.create(dataSourceProperties.getClassLoader())
				.driverClassName(dataSourceProperties.getDriverClassName())
				.url(dataSourceProperties.getUrl())
				.username(dataSourceProperties.getUsername())
				.password(dataSourceProperties.getPassword())
				.type(HikariDataSource.class)
				.build();
		dataSource.setMaximumPoolSize(maxPoolSize);
		return dataSource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()  throws NamingException{
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("io.javabrains.springbootstarter");
        entityManagerFactoryBean.setJpaProperties(jpaProperties());
        return entityManagerFactoryBean;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}
}
