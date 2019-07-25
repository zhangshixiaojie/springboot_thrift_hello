package com.demo.service;
import org.springframework.stereotype.Service;

@Service("helloService")
public class HelloServiceImpl implements HelloService.Iface{

    @Override
    public String helloString(String para) {
        return String.format("Hello %s!", para);
    }
}