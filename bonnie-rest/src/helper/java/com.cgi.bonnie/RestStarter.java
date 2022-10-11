package com.cgi.bonnie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.cgi.bonnie.businessrules.order.OrderService;
import com.cgi.bonnie.businessrules.user.UserService;
import org.springframework.context.annotation.Bean;

@ComponentScan("com.cgi.bonnie.configuration")
@SpringBootApplication
public class RestStarter {

    public static void main(String[] args) {
        SpringApplication.run(RestStarter.class, args);
    }

    @Bean
    public OrderService mockOrderService() {
        return new OrderService(null, null, null);
    }

    @Bean
    public UserService mockUserService() {
        return new UserService(null);
    }

}
