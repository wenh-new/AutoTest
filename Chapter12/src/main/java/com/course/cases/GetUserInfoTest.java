package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.DataBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {

    @Test(dependsOnGroups="loginTrue",description = "获取userId为1的用户信息")
    public void getUserInfo() throws IOException, InterruptedException {
        SqlSession session = DataBaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase",1);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);

        JSONArray resultJson = getJsonResult(getUserInfoCase);
        System.out.println("resultJson"+resultJson);

        User user = session.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);
        List userList = new ArrayList();
        userList.add(user);
        JSONArray jsonArray = new JSONArray(userList);
        //System.out.println("jsonArray"+jsonArray);
        JSONArray jsonArray1 = new JSONArray(resultJson.getString(0));
        //System.out.println("jsonArray1"+jsonArray1);
        //有的机器json串里的元素输出不一样，解决这类问题可以把元素拿出来一个个判断，assert
        Assert.assertEquals(jsonArray.toString(),jsonArray1.toString());

//        下面getUserInfoCase中expected值？？
//{"id":1,"userName":"zhangsan","password":"123456","age":"18","sex":"男","permission":"1","isDelete":"0"}
//[{"id":"2","userName":"lisi","password":"1234567","age":"25","sex": "男","permission":"1","isDelete":"0"}]


//        //下边为写完接口的代码
//        JSONArray resultJson = getJsonResult(getUserInfoCase);
//
//        /**
//         * 下边三行可以先讲
//         */
//        Thread.sleep(2000);
//        User user = session.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);
//        System.out.println("自己查库获取用户信息:"+user.toString());
//
//        List userList = new ArrayList();
//        userList.add(user);
//        JSONArray jsonArray = new JSONArray(userList);
//        System.out.println("获取用户信息:"+jsonArray.toString());
//        System.out.println("调用接口获取用户信息:"+resultJson.toString());
//        Assert.assertEquals(jsonArray,resultJson);
    }

    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("id",getUserInfoCase.getUserId());
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("调用getUserInfo返回结果："+result);
        List resultList = Arrays.asList(result);
        JSONArray jsonArray = new JSONArray(resultList);
        System.out.println("json转字符串"+jsonArray.toString());
        return jsonArray;
    }

//    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) throws IOException {
//        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
//        JSONObject param = new JSONObject();
//        param.put("id",getUserInfoCase.getUserId());
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
//        System.out.println("调用接口result:"+result);
//        List resultList = Arrays.asList(result);
//        JSONArray array = new JSONArray(resultList);
//        System.out.println(array.toString());
//        return array;
//    }
}
