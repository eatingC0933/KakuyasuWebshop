package se.yitingchang.testwebshop.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/").setViewName("user_login");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/user/register").setViewName("user_register");
        registry.addViewController("/cart").setViewName("cart");
        registry.addViewController("/orders").setViewName("orders");
        registry.addViewController("/orderDetails").setViewName("orderDetails");
        registry.addViewController("/addNewProduct").setViewName("addNewProductForm");
        registry.addViewController("/category").setViewName("category");
        registry.addViewController("/category-product").setViewName("category-product");
        registry.addViewController("/order_confirmation").setViewName("order_confirmation");
        registry.addViewController("/products").setViewName("products");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
    }



}
