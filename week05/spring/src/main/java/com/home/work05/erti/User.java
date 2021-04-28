package com.home.work05.erti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : zhu_huan
 * @createTime : 2021/4/15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String name;

    private Integer id;

    private List<String> addr;
}
