package com.home.work05.erti;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author : zhu_huan
 * @createTime : 2021/4/15
 **/
@Configuration
public class config {

    @Bean
    public User3 user3(){
        return new User3("user3",3, Arrays.asList("addr3","addr3"));
    }
}
