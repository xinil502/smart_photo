package cn.xinill.smart_photo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartPhotoApplication {

    public static void main(String[] args) {
        System.out.println("开始部署\n");
        SpringApplication.run(SmartPhotoApplication.class, args);
        System.out.println("部署成功\n");
    }

}
