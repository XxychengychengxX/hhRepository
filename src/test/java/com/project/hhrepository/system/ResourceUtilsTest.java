/**
 * @author Valar Morghulis
 * @Date 2023/8/12
 */
package com.project.hhrepository.system;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourceUtilsTest {

    @Test
    void getResourceImgTest(){
        try {
            File file = ResourceUtils.getFile("static/img/upload/banana.png");
            BufferedImage read = ImageIO.read(file);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
