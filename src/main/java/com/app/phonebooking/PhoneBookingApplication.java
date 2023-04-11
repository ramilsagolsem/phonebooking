package com.app.phonebooking;

import com.app.phonebooking.error.PhoneBookingException;
import com.app.phonebooking.service.MobileProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@EnableWebFlux
@SpringBootApplication
public class PhoneBookingApplication {

    @Autowired
    private MobileProductService mobileProductService;

    @PostConstruct
    public void init(){
        mobileProductService.loadProductDetails();
    }

    @Bean
    public WebExceptionHandler exceptionHandler() {
        return (ServerWebExchange exchange, Throwable ex) -> {
            if (ex instanceof PhoneBookingException) {
                exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
                return exchange.getResponse().setComplete();
            }
            return Mono.error(ex);
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(PhoneBookingApplication.class, args);
    }
}
