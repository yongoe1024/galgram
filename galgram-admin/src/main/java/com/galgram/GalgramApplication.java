package com.galgram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author galgram
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GalgramApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(GalgramApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  galgram 启动成功  牛皮！（压声）   \n");
    }
}
