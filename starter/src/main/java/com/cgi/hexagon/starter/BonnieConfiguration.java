package com.cgi.hexagon.starter;

import com.cgi.hexagon.authentication.auth.ApplicationUserService;
import com.cgi.hexagon.authentication.auth.FirstApplicationUserDaoService;
import com.cgi.hexagon.authentication.user.AuthUserService;
import com.cgi.hexagon.bonnierest.BonnieRestConfiguration;
import com.cgi.hexagon.businessrules.order.OrderService;
import com.cgi.hexagon.businessrules.order.OrderStorage;
import com.cgi.hexagon.businessrules.user.AuthUserStorage;
import com.cgi.hexagon.businessrules.user.UserService;
import com.cgi.hexagon.businessrules.user.UserStorage;
import com.cgi.hexagon.commplugin.kafka.KafkaMessageService;
import com.cgi.hexagon.commplugin.kafka.OrderConsumer;
import com.cgi.hexagon.commplugin.kafka.TopicConfig;
import com.cgi.hexagon.communicationplugin.MessageService;
import com.cgi.hexagon.h2storage.H2StorageApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Import({BonnieRestConfiguration.class, H2StorageApplication.class, TopicConfig.class, KafkaMessageService.class, OrderConsumer.class, FirstApplicationUserDaoService.class, AuthUserService.class})
public class BonnieConfiguration {

    @Bean
    OrderService orderService(OrderStorage orderStorage, UserStorage userStorage, MessageService sender, AuthUserStorage authUserStorage) {
        return new OrderService(orderStorage, userStorage, sender, authUserStorage);
    }

    @Bean
    UserService userService( UserStorage userStorage, AuthUserStorage authUserStorage) { return new UserService(userStorage, authUserStorage); }

    @Bean
    public ApplicationUserService applicationUserService(FirstApplicationUserDaoService service) {
        return new ApplicationUserService(service);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
