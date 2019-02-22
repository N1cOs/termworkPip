package ru.ifmo.se.termwork.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import java.util.concurrent.Executors;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"ru.ifmo.se.termwork.config", "ru.ifmo.se.termwork.service"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebConfig.class)
        })
public class RootConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Bean
    public TaskExecutor taskExecutor(){
        ConcurrentTaskExecutor taskExecutor = new ConcurrentTaskExecutor();
        taskExecutor.setConcurrentExecutor(Executors.newFixedThreadPool(4));
        return taskExecutor;
    }
}