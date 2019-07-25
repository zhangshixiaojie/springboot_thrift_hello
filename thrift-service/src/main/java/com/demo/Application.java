package com.demo;

import com.demo.service.HelloService;
import com.demo.service.HelloServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        Logger log = LoggerFactory.getLogger(Application.class);

        try {
            TProcessor tprocessor = new HelloService.Processor<>(new HelloServiceImpl());
            TServerSocket serverTransport = new TServerSocket(9898);
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TSimpleServer(tArgs);
            server.serve();
            log.info("服务端开启....");
        } catch (TTransportException e) {
            log.error("服务端开启异常.", e);
        }
    }
}