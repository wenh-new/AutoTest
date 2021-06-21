package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DataBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddUserTest {
    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口接口")
    public void addUser() throws IOException, InterruptedException {

        SqlSession session = DataBaseUtil.getSqlSession();
        AddUserCase addUserCase = session.selectOne("addUserCase",1);
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);
        System.out.println("--------分割线--------");
        //发请求，获取结果
        String result = getResult(addUserCase);
        System.out.println("result结果是："+result);

        //以下验证返回结果
        //涉及到俩个程序，有可能还没添加完用户就去查了，所以防止查不到，添加下面sleep代码
        Thread.sleep(3000);
        User user = session.selectOne("addUser",addUserCase);
        System.out.println(user.toString());
        //处理结果，就是判断返回结果是否符合预期
        Assert.assertEquals(addUserCase.getExpected(),result);




//        //下边的代码为写完接口的测试代码
//        String result = getResult(addUserCase);
//
//        /**
//         * 可以先讲
//         */
//        //查询用户看是否添加成功
//        Thread.sleep(2000);
//        User user = session.selectOne("addUser",addUserCase);
//        System.out.println(user.toString());
//
//        //处理结果，就是判断返回结果是否符合预期
//        Assert.assertEquals(addUserCase.getExpected(),result);
    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        JSONObject param = new JSONObject();
        param.put("userName",addUserCase.getUserName());
        param.put("password",addUserCase.getPassword());
        param.put("sex",addUserCase.getSex());
        param.put("age",addUserCase.getAge());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete",addUserCase.getIsDelete());
        //设置头信息
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        //存放返回结果
        String result;
        HttpResponse response= TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        return result;
    }

//    private String getResult(AddUserCase addUserCase) throws IOException {
//        //下边的代码为写完接口的测试代码
//        HttpPost post = new HttpPost(TestConfig.addUserUrl);
//        JSONObject param = new JSONObject();
//        param.put("userName",addUserCase.getUserName());
//        param.put("password",addUserCase.getPassword());
//        param.put("sex",addUserCase.getSex());
//        param.put("age",addUserCase.getAge());
//        param.put("permission",addUserCase.getPermission());
//        param.put("isDelete",addUserCase.getIsDelete());
//        //设置请求头信息 设置header
//        post.setHeader("content-type","application/json");
//        //将参数信息添加到方法中
//        StringEntity entity = new StringEntity(param.toString(),"utf-8");
//        post.setEntity(entity);
//        //设置cookies
//        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
//        //声明一个对象来进行响应结果的存储
//        String result;
//        //执行post方法
//        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
//        //获取响应结果
//        result = EntityUtils.toString(response.getEntity(),"utf-8");
//        System.out.println(result);
//        return result;
//    }
}
