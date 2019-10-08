package io.pivotal.tdddemo.controller;

import io.pivotal.tdddemo.dao.HelloDao;
import io.pivotal.tdddemo.service.HelloService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private HelloController controller;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private HelloDao helloDao;

    @SpyBean
    private HelloService service;

    @Test
    public void testHello() {
        when(helloDao.getHello()).thenReturn("hello world\n");

//        when(dao.getHello()).thenReturn("hello");

        String message = controller.getHello();
        assertEquals("hello world\n", message);

    }

    @Test
    public void testHelloMvc() throws Exception {
        when(helloDao.getHello()).thenReturn("hello world\n");


        String resp = testRestTemplate.getForObject("http://localhost:" + port + "/hello", String.class);

        assertEquals("hello world\n", resp);
    }

    @Test
    public void testLogMessage() {
        when(helloDao.getHello()).thenReturn("hello world\n");

        String message = controller.getHello();
        assertEquals("hello world\n", message);

        verify(service, times(1)).logMessage(anyString());
    }

    @Test
    public void testLogMessageDidntGetCalled() {
        when(helloDao.getHello()).thenReturn("world\n");

        String message = controller.getHello();

        verify(service, never()).logMessage(anyString());
    }

    @Test
    public void testArgumentCapture() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        when(helloDao.getHello()).thenReturn("hello world\n");

        controller.getHello();

        verify(service).logMessage(captor.capture());

        assertEquals("hello world\n", captor.getValue());
    }
}
