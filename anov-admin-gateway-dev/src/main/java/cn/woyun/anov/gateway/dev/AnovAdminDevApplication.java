package cn.woyun.anov.gateway.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * AnovAdminDev的程序启动入口。
 *
 * @author xuepeng
 */
@SpringBootApplication
@ComponentScan("cn.woyun.anov.*")
public class AnovAdminDevApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnovAdminDevApplication.class, args);
    }

}
