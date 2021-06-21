package com.course.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value = "/",description ="这是我全部的get方法" )
public class MyGetMethod {

    //服务端响应返回cookies的get请求
    @RequestMapping(value = "/getcookies",method = RequestMethod.GET)
    @ApiOperation(value = "通过这个方法可以获取到Cookies",httpMethod = "Get")
    public String getcookies(HttpServletResponse response){
        //HttpServletResponse 装响应信息的类
        //HttpServletRequest 装请求信息的类
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        return "SpringBoot实现get接口并返回cookies信息成功";
    }

    /**
     * 要求客户端携带cookies信息访问
     * 这是一个要求携带cookies信息才能访问的get请求
     */
    @RequestMapping(value = "getwithcookies",method = RequestMethod.GET)
    @ApiOperation(value = "要求客户端携带cookies信息访问",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies)){
            return "你必须携带一个cookies信息才能访问";
        }
        for (Cookie cookie:cookies) {
            if(cookie.getName().equals("login")&&cookie.getValue().equals("true")){
                return "恭喜你，cookies正确";
            }
        }
        return "cookies错误";
    }

    /**
     * 开发一个需要携带参数才能访问的get请求
     * 第一种实现方式 url：/getwithparam?key=value&key=value
     * 我们来模拟获取商品列表
     */
    @RequestMapping(value = "/getwithparam",method = RequestMethod.GET)
    @ApiOperation(value = "需要携带参数才能访问的get请求的第一种实现",httpMethod = "GET")
    public Map<String,Integer> getList(@RequestParam Integer start,
                                       @RequestParam Integer end){
        Map<String,Integer> mylist = new HashMap<>();
        mylist.put("鞋子",400);
        mylist.put("干脆面",2);
        mylist.put("衣服",300);
        return mylist;
    }
    /**
     * 第二种实现方式 url：/getwithparam/10/20
     */
    @RequestMapping(value = "/getwithparam/{start}/{end}",method = RequestMethod.GET)
    @ApiOperation(value = "需要携带参数才能访问的get请求的第二种实现",httpMethod = "GET")
    public Map<String,Integer> getmyList(@PathVariable Integer start,
                                         @PathVariable Integer end){
        Map<String,Integer> mylist = new HashMap<>();
        mylist.put("鞋子",4);
        mylist.put("干脆面",2);
        mylist.put("衣服",3);
        return mylist;

    }
}
