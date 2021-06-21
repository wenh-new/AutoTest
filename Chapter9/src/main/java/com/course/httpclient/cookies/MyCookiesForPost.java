package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {
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
    public void testPostCookies() throws IOException {
        //测试地址的拼接
        String testurl = this.url+bundle.getString("test.post.with.cookies");
        //声明一个client对象，用来方法的执行
        DefaultHttpClient client = new DefaultHttpClient();
        //声明一个post方法对象
        HttpPost post = new HttpPost(testurl);

        //添加参数（服务器需要json格式的参数，先创建一个json对象，pom需要引入json依赖）
        JSONObject param = new JSONObject();
        param.put("name","huhansan");
        param.put("age","20");

        //设置请求头信息，设置headers
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中,将JSONObject对象 转换为 String 对象，然后声明一个StringEntity对象
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        //设置cookies信息
        client.setCookieStore(this.store);
        //执行post方法,获取响应结果,并转换为String类型,
        HttpResponse response = client.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        //处理结果，就是判断返回结果是否符合预期
        //将返回的响应结果字符串转换为json对象
        JSONObject resultJson = new JSONObject(result);
        //获取到结果值
        String success = resultJson.getString("huhansan");
        String status = resultJson.getString("status");
        //具体的判断返回结果的值
        Assert.assertEquals(success,"success");
        Assert.assertEquals(status,"1");

    }
}
