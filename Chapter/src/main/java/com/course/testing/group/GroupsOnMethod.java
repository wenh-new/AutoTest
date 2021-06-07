package com.course.testing.group;


import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

public class GroupsOnMethod {
    @Test(groups = "group1")
    public void test1(){
        System.out.println("组一");
    }
    @Test(groups = "group1")
    public void test2(){
        System.out.println("组一一");
    }
    @Test(groups = "group2")
    public void test3(){
        System.out.println("组二");
    }
    @Test(groups = "group2")
    public void test4(){
        System.out.println("组二二");
    }
    @BeforeGroups("group1")
    public void beforegroup(){
        System.out.println("before1111");
    }
    @AfterGroups("group1")
    public void aftergroup(){
        System.out.println("after1111");
    }
}
