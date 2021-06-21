package com.course.config;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestConfig {
    //登录接口url
    public static String loginUrl;
    //更新用户信息接口url
    public static String updateUserInfoUrl;
    //获取用户列表接口url
    public static String getUserListUrl;
    //获取用户信息接口url
    public static String getUserInfoUrl;
    //添加用户信息接口url
    public static String addUserUrl;
    //声明http客户端
    public static DefaultHttpClient defaultHttpClient;
    //用来存储cookies信息的变量
    public static CookieStore cookieStore;
}
