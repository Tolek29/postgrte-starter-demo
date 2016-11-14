package com.gm.autoconfigure;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by gmurias on 06.11.16.
 */
@Configuration
@EnableConfigurationProperties(HibProperties.class)
public class PostgresqlConfig implements EnvironmentAware {


    private Environment environment;

    @Autowired
    private HibProperties properties;


    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.show_sql}")
    private String hibernateShowSql;

    @Value("${hibernate.format_sql}")
    private String hibernateFormaqtSql;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddlAuto;

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        return new TomcatEmbeddedServletContainerFactory() {

            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
                    Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatEmbeddedServletContainer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setName("jdbc/calendarDB");
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName", "org.postgresql.Driver");
                resource.setProperty("username", "vagrant");
                resource.setProperty("password", "vagrant");
                resource.setProperty("url", "jdbc:postgresql://127.0.0.1:5434/vagrant?currentSchema=public");

                context.getNamingResources().addResource(resource);
            }
        };
    }


    @Bean
    public DataSource jndiDataSource() throws NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/jdbc/calendarDB");
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.afterPropertiesSet();
        return (DataSource)bean.getObject();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws NamingException {
        LocalSessionFactoryBean session = new LocalSessionFactoryBean();
        session.setDataSource(jndiDataSource());
        session.setHibernateProperties(hibProperties());
        return session;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));


        return properties;
    }

//    @Bean
//    public PersistenceAnnotationBeanPostProcessor postProcessor () {
//        return new PersistenceAnnotationBeanPostProcessor();
//    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

}
