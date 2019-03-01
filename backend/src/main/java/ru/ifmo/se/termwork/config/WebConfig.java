package ru.ifmo.se.termwork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import ru.ifmo.se.termwork.support.resolver.JsonParamArgumentResolver;

import javax.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"ru.ifmo.se.termwork.controller"})
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/*").addResourceLocations("/static");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JsonParamArgumentResolver());
    }

    @Bean
    public AcceptHeaderLocaleResolver localeResolver(){
        Locale defaultLocale = new Locale("ru");
        List<Locale> locales = Collections.singletonList(defaultLocale);

        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(defaultLocale);
        localeResolver.setSupportedLocales(locales);
        return localeResolver;
    }

    @Bean
    public Validator validator(){
        return new LocalValidatorFactoryBean();
    }
}
