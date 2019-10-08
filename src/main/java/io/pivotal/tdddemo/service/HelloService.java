package io.pivotal.tdddemo.service;

import io.pivotal.tdddemo.dao.HelloDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @Autowired
    private HelloDao helloDao;

    public String getHello() {
        String message = helloDao.getHello();

        if (message != null && message.contains("hello")) {
            logMessage(message);
        }

        return message;
    }

    public void logMessage(String message) {
        System.out.println(message);
    }
}
