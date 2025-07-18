package com.took.egg_plant_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /upload/** 요청이 오면 C:/upload/ 디렉토리의 파일을 서빙
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///C:/upload/");
    }
}
