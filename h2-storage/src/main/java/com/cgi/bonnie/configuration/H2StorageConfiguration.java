package com.cgi.bonnie.configuration;

import com.cgi.bonnie.businessrules.order.OrderService;
import com.cgi.bonnie.businessrules.order.OrderStorage;
import com.cgi.bonnie.businessrules.user.UserService;
import com.cgi.bonnie.businessrules.user.UserStorage;
import com.cgi.bonnie.communicationplugin.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@ComponentScan({"com.cgi.bonnie.h2storage.user", "com.cgi.bonnie.h2storage.order"})
@EnableJdbcRepositories({"com.cgi.bonnie.h2storage.user", "com.cgi.bonnie.h2storage.order"})
public class H2StorageConfiguration {

	@Bean
	OrderService orderService(OrderStorage orderStorage, UserStorage userStorage, MessageService sender) {
		return new OrderService(orderStorage, userStorage, sender);
	}

	@Bean
	UserService userService(UserStorage userStorage) { return new UserService(userStorage); }

}
