package com.course.testing.group;

import org.testng.annotations.Test;

@Test(groups = "tea")
public class GroupOnClass3 {
    public void teacher1() {
        System.out.println("Class3中的t1运行");
    }
    public void teacher2() {
        System.out.println("Class3中的t2运行");
    }
}
