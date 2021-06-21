package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.xml.ws.Response;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {
    private String url;
    private ResourceBundle bundle;
    //用来存储cookies信息的变量
    private CookieStore store;

    @BeforeTest
    public void beforeTest(){
        //通过工具类ResourceBundle读取配置文件application
         bundle = ResourceBundle.getBundle("application",Locale.CANADA);
         //拿到服务器地址
         url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException {
        String result;
        //拼接测试地址:服务器地址+文件地址
        String testUrl = this.url+bundle.getString("test.get.cookies");
        //发送get请求，访问地址，接收响应
        HttpGet get = new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(get);
        //响应实体转换为字符串格式并输出
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        //获取cookies信息
        this.store = client.getCookieStore();
        List<Cookie> cookieList = store.getCookies();
        //打印输出cookie
        for (Cookie cookie:cookieList) {
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name ="+name+" ;cookie value ="+value);
        }
    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testGetwithCookies() throws IOException {
        //拼接地址
        String testUrl = this.url+bundle.getString("test.get.with.cookies");
        HttpGet get = new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        //设置cookies信息
        client.setCookieStore(this.store);

        HttpResponse response = client.execute(get);
        //获取响应的状态码
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode = "+statusCode);
        if (statusCode == 200){
            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        }
    }
}
