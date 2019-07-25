package com.demo.service;

import com.demo.AbstractTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloServiceTest extends AbstractTest {

    @Autowired
    private HelloService.Iface helloService;

    private static Logger log = LoggerFactory.getLogger(HelloServiceTest.class);


    @Test
    public void testLocal() {
        try {
            log.info("本地调用服务...{}", helloService.helloString("Local"));
        } catch (TException e) {
            log.error("本地调用异常.", e);
        }
    }


    @Test
    public void testRemote() {
        try (TTransport transport = new TSocket("localhost", 9898, 30000)) {
            TProtocol protocol = new TBinaryProtocol(transport);
            HelloService.Client helloService = new HelloService.Client(protocol);
            transport.open();
            log.info("远程调用服务...{}", helloService.helloString("Remote"));
        } catch (TException e) {
            log.error("远程调用异常.", e);
        }
    }

}
