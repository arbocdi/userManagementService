package net.sf.userManagementService;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.sql.SQLException;

@SpringBootApplication
@EnableWebFlux
public class UserManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagementServiceApplication.class, args);
    }

//    @Bean(initMethod = "start", destroyMethod = "stop")
//    public Server inMemoryH2DatabaseaServer() throws SQLException {
//        return Server.createTcpServer(
//                "-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
//    }

}

