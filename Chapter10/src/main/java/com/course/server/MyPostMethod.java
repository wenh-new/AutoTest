package com.course.server;

import com.course.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/",description = "这是我全部的post请求")
@RequestMapping("v1")
public class MyPostMethod {
    //声明一个cookie变量,用来装cookies信息
    private  Cookie cookie;

    /*场景：用户登录成功获取到cookies，然后再访问其他接口获取到列表*/
    //这是一个返回cookie信息的post请求
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "登录接口，成功后获取cookies信息",httpMethod = "POST")
    public String login(HttpServletResponse response,
                        @RequestParam(value = "userName",required = true) String uesrName,
                        @RequestParam(value = "passWord",required = true) String passWord){
        //正常在数据库进行验证，但是没有数据库，这里先写死
        if(uesrName.equals("zhangsan")&&passWord.equals("123456")){
            cookie = new Cookie("login","true");
            response.addCookie(cookie);
            return "用户名密码正确，登录成功";
    }
        return "用户名或者密码错误";
    }

    //携带cookies信息访问的post方法，验证cookies并返回用户列表
    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表",httpMethod = "POST")
    public String getUserList (HttpServletRequest request,
                             @RequestBody User u){
        User user;
        //获取cookies
        Cookie[] cookies = request.getCookies();
        //验证cookies是否合法
        for(Cookie c:cookies){
            if(c.getName().equals("login")
                    &&c.getValue().equals("true")
                    &&u.getUserName().equals("zhangsan")
                    &&u.getPassword().equals("123456")){
                user = new User();
                user.setName("lishi");
                user.setAge(19);
                user.setSex("man");
                return  user.toString();

            }
        }
        return  "参数不合法";
    }
}
