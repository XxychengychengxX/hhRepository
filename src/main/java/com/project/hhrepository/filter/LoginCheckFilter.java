/**
 * @author Valar Morghulis
 * @Date 2023/7/24
 */
package com.project.hhrepository.filter;

import com.alibaba.fastjson.JSON;
import com.project.hhrepository.entity.Result;
import com.project.hhrepository.utils.WarehouseConstants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义的登录限制过滤器
 */
public class LoginCheckFilter implements Filter {

    private StringRedisTemplate redisTemplate;

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletRequest;

        //1.白名单请求直接放行,创建List集合直接放行白名单

        ArrayList<String> urlList = new ArrayList<>();
        urlList.add("/captcha/captchaImage");
        urlList.add("/login");
        //2.检测是不是白名单请求
        String servletPath = req.getServletPath();
        if (urlList.contains(servletPath)) {
            filterChain.doFilter(req, resp);
        }

        //3.如果不是,查看其他请求是否携带token
        String token =
                ((HttpServletRequest) servletRequest).getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        // >3.1 如果有则证明已经登录成功
        if (StringUtils.hasText(token) && redisTemplate.hasKey(token))
        {
            filterChain.doFilter(req,resp);
        }
        else {
            HashMap<String, Object> data = new HashMap<String, Object>();
            Result err = Result.err(Result.CODE_ERR_UNLOGINED, "您尚未登录!");
            String respStr = JSON.toJSONString(err);

            resp.setContentType("application/json;charset=utf-8");
            PrintWriter writer = resp.getWriter();
            writer.write(respStr);
            writer.flush();
            writer.close();
        }

    }
}
