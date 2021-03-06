package by.eco_house.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Configuration class for Spring MVC
 */
@Configuration
@EnableWebMvc
@ComponentScan("by.eco_house")
public class WebConfig implements WebMvcConfigurer {

    private final String corsOrigin = "http://localhost:4200";

    /**
     * Bean for message source.
     * Set params: package with massages property file? encoding.
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("*").addResourceLocations("");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/login").setViewName("forward:/index.html");
        registry.addViewController("/login/**").setViewName("forward:/index.html");
        registry.addViewController("/signup").setViewName("forward:/index.html");
        registry.addViewController("/signup/**").setViewName("forward:/index.html");
        registry.addViewController("/api").setViewName("forward:/index.html");
        registry.addViewController("/api/**").setViewName("forward:/index.html");
        registry.addViewController("/auctions").setViewName("forward:/index.html");
        registry.addViewController("/auctions/**").setViewName("forward:/index.html");
        registry.addViewController("/management").setViewName("forward:/index.html");
        registry.addViewController("/management/**").setViewName("forward:/index.html");
        registry.addViewController("/administration").setViewName("forward:/index.html");
        registry.addViewController("/administration/**").setViewName("forward:/index.html");
        registry.addViewController("/eco_house/start").setViewName("forward:/index.html");
        registry.addViewController("/eco_house/start/**").setViewName("forward:/index.html");
        registry.addViewController("/user").setViewName("forward:/index.html");
        registry.addViewController("/user/**").setViewName("forward:/index.html");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedOrigins(corsOrigin)
                .allowedHeaders(
                        HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
                        HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                        HttpHeaders.CONTENT_TYPE,
                        HttpHeaders.AUTHORIZATION,
                        HttpHeaders.ACCEPT
                )
                .allowCredentials(true)
                .maxAge(3600);
    }

}
