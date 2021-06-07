package com.course.testing.group;

import org.testng.annotations.Test;

@Test(groups = "stu")
public class GroupOnClass1 {
    public void student1() {
        System.out.println("Class1中的s1运行");
    }
    public void student2() {
        System.out.println("Class1中的s2运行");
    }
}
