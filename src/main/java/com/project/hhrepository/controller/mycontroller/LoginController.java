/**
 * @author Valar Morghulis
 * @Date 2023/7/22
 */
package com.project.hhrepository.controller.mycontroller;

import com.google.code.kaptcha.Producer;
import com.project.hhrepository.entity.LoginUser;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.service.IAuthInfoService;
import com.project.hhrepository.service.IUserInfoService;
import com.project.hhrepository.utils.CurrentUser;
import com.project.hhrepository.utils.TokenUtils;
import com.project.hhrepository.utils.WarehouseConstants;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class LoginController {

    @Resource
    IUserInfoService userInfoService;
    @Resource
    IAuthInfoService authInfoService;

    @Resource
    TokenUtils tokenUtils;
    @Resource(name = "captchaProducer")
    private Producer producer;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @GetMapping("/captcha/captchaImage")
    public void captchaImage(HttpServletResponse response) {

        //生成验证码图片的文件
        String text = producer.createText();
        //使用验证码文本生成验证码图片 -- BufferedImage对象就代表生成验证码的图片，在内存中
        BufferedImage image = producer.createImage(text);

        stringRedisTemplate.opsForValue().set(text, "", 60 * 30, TimeUnit.SECONDS);

        //设置相应的格式
        response.setContentType("image/jpeg");
        //写出到前端
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            //将验证码图片写给前端
            ImageIO.write(image, "jpeg", outputStream);
            //刷新
            outputStream.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @PostMapping("/login")
    public Result userLogin(@RequestBody LoginUser loginUser) {
        return userInfoService.userLogin(loginUser);
    }

    /**
     * 获取当前登录用户信息接口
     *
     * @param token 在请求头中包含的token令牌
     * @return 成功的Result对象
     */
    @GetMapping("/curr-user")
    public Result currentUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        return userInfoService.getCurrentUser(token);
    }

    @GetMapping("/user/auth-list")
    public Result getAuthTree(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {

        //获取当前登录用户id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();

        //执行业务并返回
        return Result.ok(authInfoService.getAuthTreeByUid(userId));
    }

    @PostMapping("/logout")
    public Result logout(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //直接删除redisTemplate中的值即可
        stringRedisTemplate.delete("token");

        return Result.ok("您已退出登录!");
    }
}
