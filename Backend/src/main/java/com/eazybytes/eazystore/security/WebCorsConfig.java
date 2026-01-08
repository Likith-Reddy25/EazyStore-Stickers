//package com.eazybytes.eazystore.security;
//
////import lombok.Value;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.List;
//
//@Configuration
//public class WebCorsConfig {
//
//    @Value("${frontend.url}")
//    private String frontendUrl;
//
//    @Bean
//    public FilterRegistrationBean<CorsFilter> corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//
//
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(List.of(
//                "http://localhost:5173"
//        ));
//        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
//        config.setAllowedHeaders(List.of("*"));
//        config.setExposedHeaders(List.of("Authorization"));
//
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//
//        FilterRegistrationBean<CorsFilter> bean =
//                new FilterRegistrationBean<>(new CorsFilter(source));
//
//        bean.setOrder(0); // 🔴 ABSOLUTE HIGHEST PRIORITY
//        return bean;
//    }
//}
