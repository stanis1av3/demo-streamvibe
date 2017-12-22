package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StreamvibeintegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamvibeintegrationApplication.class, args);
    }


//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.build();
//    }
//
////    @Bean
////    public BaseService baseService() {
////        return new BaseService();
////    }
////
////
////    @Bean
////    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
////        return args -> {
////            baseService().run();
////        };
////    }
}
