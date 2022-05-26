package so.debateit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${custom.path.uploadImage}")
    private String uploadPath;

    @Value("${custom.path.uploadRoot}")
    private String uploadRoot;



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler(uploadRoot).addResourceLocations(uploadPath).setCachePeriod(20);
    }
}

