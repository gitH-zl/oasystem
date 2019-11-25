package com.exoa.oasystem.configurerAdapter;

import com.exoa.oasystem.interceptor.TimeCostInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import java.nio.charset.Charset;

/**
 * @author zlian
 * @date 2019/7/17  14:32
 * @Description: TODO
 */
//@Component
public class MvcAdapter extends WebMvcConfigurationSupport {
    @Resource
    private TimeCostInterceptor timeCostInterceptor;

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // super.addInterceptors(registry);
        registry.addInterceptor(timeCostInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}
