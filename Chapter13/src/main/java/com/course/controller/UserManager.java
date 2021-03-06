package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName UserManager
 * @Description TODO
 * @Author lixinwei
 * @Date 2021/8/25 2:41 下午
 * @Version 1.0
 **/
@Slf4j
@RestController
@Api(value = "v1", description = "用户管理系统")
@RequestMapping("v1")
public class UserManager {

    @Autowired
    private SqlSessionTemplate template;

    @ApiOperation(value = "登录接口", httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Boolean login(HttpServletResponse response, @RequestBody User user) {
        int i = template.selectOne("login", user);
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        log.info("查询到的结果是" + i);

        if (i == 1) {
            log.info("登录的用户是：" + user.getUserName());
            return true;
        }
        return false;
    }

    @ApiOperation(value = "添加用户接口", httpMethod = "POST")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public boolean addUser(HttpServletRequest request, @RequestBody User user) {
        Boolean pass = verrfyCookies(request);
        int result = 0;
        if (pass) {
            result = template.insert("addUser",user);
            template.flushStatements();
        }
        if (result > 0) {
            log.info("添加用户的数量是：" + result);
            return true;
        }
        return false;
    }

    @ApiOperation(value = "获取用户（列表）信息接口", httpMethod = "POST")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public List<User> getUserList(HttpServletRequest request,@RequestBody User user) {
        Boolean pass = verrfyCookies(request);

        if (pass) {
            List<User> users = template.selectList("getUserInfo", user);
            template.flushStatements();
            log.info("getUserInfo获取到的用户数量是：" + users.size());
            return users;
        }

        return null;
    }

    @ApiOperation(value = "更新/删除用户接口", httpMethod = "POST")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public int updateUser(HttpServletRequest request, @RequestBody User user) {
        Boolean pass = verrfyCookies(request);

        int i = 0;//更新了几条

        if (pass) {
            i = template.update("updateUserInfo", user);
            template.flushStatements();
        }

        log.info("更新数据的条目数为：" + i);
        return i;
    }

    private Boolean verrfyCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            log.info("cookies为空");
            return false;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")) {
                log.info("cookies验证通过");
                return true;
            }
        }

        return false;
    }
}

