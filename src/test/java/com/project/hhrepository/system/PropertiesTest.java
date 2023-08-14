/**
 * @author Valar Morghulis
 * @Date 2023/8/6
 */
package com.project.hhrepository.system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PropertiesTest {
    @Value("${file.upload-path")
    String a;

    @Test
    public void printProp(){
        System.out.println(a);
    }
}
