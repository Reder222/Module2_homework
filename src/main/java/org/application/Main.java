package org.application;

import org.application.configuration.SpringConfiguration;
import org.application.consoleApplication.ConsoleDatabaseApplication;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main {



    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        ConsoleDatabaseApplication application = context.getBean(ConsoleDatabaseApplication.class);
        application.run();

    }
}
