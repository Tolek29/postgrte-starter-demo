package com.gm.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;

/**
 * Created by gmurias on 14.11.16.
 */
@Configuration
public class PersistanceConfig {
    @Bean
    public PersistenceAnnotationBeanPostProcessor postProcessor () {
        return new PersistenceAnnotationBeanPostProcessor();
    }
}
