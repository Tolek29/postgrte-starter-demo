package com.gm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by gmurias on 06.11.16.
 */
@SpringBootApplication()
public class Start {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Start.class, args);
        String[] beanDefinitionNames = ctx.getBeanFactory().getBeanDefinitionNames();
        System.out.println("");
        for(String name: beanDefinitionNames) {
            System.out.println("BEAN NAME: " + name);
        }
    }
}
