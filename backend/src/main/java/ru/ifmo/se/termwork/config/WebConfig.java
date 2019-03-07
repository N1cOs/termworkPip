package ru.ifmo.se.termwork.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import ru.ifmo.se.termwork.support.interceptor.RecomputeInterceptor;
import ru.ifmo.se.termwork.support.resolver.JsonParamArgumentResolver;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@EnableWebMvc
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"ru.ifmo.se.termwork.controller"})
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("public/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JsonParamArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(objectMapper());
        converters.add(messageConverter);
    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public ObjectMapper objectMapper(){
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(hibernate5Module);
        return mapper;
    }

    @Bean
    public HandlerInterceptor interceptor(){
        return new RecomputeInterceptor();
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
