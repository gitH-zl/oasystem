package com.exoa.oasystem.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zliang
 * @date 2019/7/17  11:22
 * @Description: TODO
 */
@Slf4j
@RestController
public class OaDemoController {
    // private  static  final  log=  ;

    @GetMapping("/hello/{ad}")
    public String hello(@PathVariable("ad") Integer integer, String string) {
        String string1 = "zhangliang";
        System.out.println();
        log.info("------" + integer);
        if (integer == 5) {
            System.out.println("zz"+integer);
        }
        char charStr = string1.charAt(1);
        System.out.println(charStr);
        return "hello world!";
    }

}
