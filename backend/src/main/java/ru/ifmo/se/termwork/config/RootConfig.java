package ru.ifmo.se.termwork.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@ComponentScan(basePackages = {"ru.ifmo.se.termwork.config", "ru.ifmo.se.termwork.service"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebConfig.class)
        })
public class RootConfig {

        @Bean
        public MessageSource messageSource(){
                ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
                messageSource.setBasename("classpath:l10n/messages");
                messageSource.setDefaultEncoding("UTF-8");
                messageSource.setUseCodeAsDefaultMessage(true);
                return messageSource;
        }
}