package jp.tier4.dataconversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * アプリケーション基底クラス
 *
 *
 * @version 0.0.1
 * @since 0.0.1
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
