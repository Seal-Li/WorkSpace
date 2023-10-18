package com.tsingtao.homework;

import com.pig4cloud.pig.common.feign.annotation.EnablePigFeignClients;
import com.pig4cloud.pig.common.security.annotation.EnablePigResourceServer;
import com.pig4cloud.pig.common.swagger.annotation.EnablePigDoc;
import org.dromara.easyes.starter.register.EsMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnablePigDoc("homework")
@EnablePigFeignClients(basePackages = {"com.pig4cloud.pig", "com.tsingtao.homework"})
@EnablePigResourceServer
@EnableDiscoveryClient
@SpringBootApplication
@EsMapperScan("com.tsingtao.homework.mapper")
public class HomeworkEsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkEsApplication.class, args);
    }

}
