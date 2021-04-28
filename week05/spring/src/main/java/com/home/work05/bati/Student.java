package com.home.work05.bati;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : zhu_huan
 * @createTime : 2021/4/15
 **/
@Component
@ConfigurationProperties(prefix = "student")
@Data
public class Student {

    private String name;

    private Integer id;
}
