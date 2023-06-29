package com.wayplus.waytraveler.config;

import com.wayplus.waytraveler.config.handler.TokenAuthorizeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.wayplus.waytraveler")
public class ApplicationConfig implements WebMvcConfigurer {

    @Value("${upload.path.file}")
    String externalFileUploadPath;
    @Value("${upload.path.image}")
    String externalImageUploadPath;

    @Autowired
    private TokenAuthorizeInterceptor tokenAuthorizeInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/upload/file/**")
                .setCacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .addResourceLocations("file:///"+ externalFileUploadPath);
        registry.addResourceHandler("/upload/images/**")
                .setCacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .addResourceLocations("file:///"+externalImageUploadPath);

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        registry.addResourceHandler("/plugin/**")
                .addResourceLocations("classpath:/static/plugin/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        List<String> excludeUrl = new ArrayList<>();
        excludeUrl.add("/plugin/**");
        excludeUrl.add("/images/**");
        excludeUrl.add("/css/**");
        excludeUrl.add("/js/**");
        excludeUrl.add("/upload/**");
        excludeUrl.add("/error");
        excludeUrl.add("/favicon.ico");

        registry.addInterceptor(tokenAuthorizeInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrl);
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        AtomicReference<TomcatServletWebServerFactory> factory = new AtomicReference<>(new TomcatServletWebServerFactory());
        factory.get().addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "|{}"));

        return factory.get();
    }

    @Bean
    public MappingJackson2JsonView jsonView(){
        MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();
        mappingJackson2JsonView.setContentType("application/json;charset=utf-8");
        mappingJackson2JsonView.setPrettyPrint(true);
        return mappingJackson2JsonView;
    }
}
