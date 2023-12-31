package com.globant.dojo.msloginaugt.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MapperResolver {

    private static ApplicationContext context = buildContext();

    private static ApplicationContext buildContext() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(TestMapperConfiguration.class);
        annotationConfigApplicationContext.refresh();
        return annotationConfigApplicationContext;
    }

    public static <T> T getMapper(Class<T> clazz) {
        return context.getBean(clazz);
    }
}
