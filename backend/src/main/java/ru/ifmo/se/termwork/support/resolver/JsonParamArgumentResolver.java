package ru.ifmo.se.termwork.support.resolver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ru.ifmo.se.termwork.support.annotation.JsonParam;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

public class JsonParamArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(JsonParam.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        JsonParam annotation = methodParameter.getParameterAnnotation(JsonParam.class);
        String jsonKey = annotation.value();
        String defaultValue = annotation.defaultValue();

        BufferedReader reader = nativeWebRequest.getNativeRequest(HttpServletRequest.class).getReader();
        Class<?> type = methodParameter.getParameter().getType();

        if (reader != null && reader.ready()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode object = objectMapper.readTree(reader);
            JsonNode jsonValue = object.get(jsonKey);

            if(jsonValue != null){
                Object arg = parseArgument(type, jsonValue.textValue());
                if(arg != null)
                    return arg;
            }
        }

        if (!defaultValue.isEmpty()) {
            Object arg = parseArgument(type, defaultValue);
            if(arg != null)
                return arg;

        } else if (type.isPrimitive()) {
            if (type.isAssignableFrom(boolean.class))
                return Boolean.FALSE;
            return 0;
        }
        return null;
    }

    private Object parseArgument(Class<?> type, String value) {
        if (type.isAssignableFrom(String.class))
            return value;
        else if (type.isAssignableFrom(int.class) || type.isAssignableFrom(Integer.class))
            return Integer.parseInt(value);
        else if(type.isAssignableFrom(long.class) || type.isAssignableFrom(Long.class))
            return Long.parseLong(value);
        else if (type.isAssignableFrom(double.class) || type.isAssignableFrom(Double.class))
            return Double.parseDouble(value);
        return null;
    }
}
