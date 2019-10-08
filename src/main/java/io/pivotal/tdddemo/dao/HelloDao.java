package io.pivotal.tdddemo.dao;

import org.springframework.stereotype.Repository;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Repository
public class HelloDao {

    public String getHello() {
        try {
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            Files.copy(Paths.get("some-file.txt"), io);
            return new String(io.toByteArray(), "UTF-8");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
