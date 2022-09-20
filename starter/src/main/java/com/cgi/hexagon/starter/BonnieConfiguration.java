package com.cgi.hexagon.starter;


import com.cgi.hexagon.bonnierest.BonnieRestConfiguration;
import com.cgi.hexagon.businessrules.order.OrderStorage;
import com.cgi.hexagon.businessrules.order.OrderService;
import com.cgi.hexagon.businessrules.user.UserService;
import com.cgi.hexagon.businessrules.user.UserStorage;
import com.cgi.hexagon.kafka.messages.Sender;
import com.cgi.hexagon.h2storage.H2StorageApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({BonnieRestConfiguration.class, H2StorageApplication.class, Sender.class})
public class BonnieConfiguration {

    @Bean
    OrderService orderService(OrderStorage orderStorage, UserStorage userStorage, Sender sender) {
        return new OrderService(orderStorage, userStorage, sender);
    }

    @Bean
    UserService userService(com.cgi.hexagon.businessrules.user.UserStorage userStorage) { return new UserService(userStorage); }
}
