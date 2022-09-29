package com.cgi.hexagon.configuration;

import com.cgi.hexagon.businessrules.order.OrderService;
import com.cgi.hexagon.businessrules.order.OrderStorage;
import com.cgi.hexagon.businessrules.user.UserService;
import com.cgi.hexagon.businessrules.user.UserStorage;
import com.cgi.hexagon.communicationplugin.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@ComponentScan({"com.cgi.hexagon.h2storage.user", "com.cgi.hexagon.h2storage.order"})
@EnableJdbcRepositories({"com.cgi.hexagon.h2storage.user", "com.cgi.hexagon.h2storage.order"})
public class H2StorageConfiguration {

	@Bean
	OrderService orderService(OrderStorage orderStorage, UserStorage userStorage, MessageService sender) {
		return new OrderService(orderStorage, userStorage, sender);
	}

	@Bean
	UserService userService(UserStorage userStorage) { return new UserService(userStorage); }

}
