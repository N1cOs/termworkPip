package ru.ifmo.se.termwork.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = {"ru.ifmo.se.termwork.config"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebConfig.class)
})
public class RootConfig {
}
