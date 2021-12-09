package cn.woyun.anov.gateway.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * AnovAdminManagement的程序启动入口。
 *
 * @author xuepeng
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan("cn.woyun.anov.*")
@EnableAsync
public class AnovAdminManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnovAdminManagementApplication.class, args);
    }

}
