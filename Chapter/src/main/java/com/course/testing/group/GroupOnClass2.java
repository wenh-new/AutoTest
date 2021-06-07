package com.course.testing.group;

import org.testng.annotations.Test;

@Test(groups = "stu")
public class GroupOnClass2 {
    public void student1() {
        System.out.println("Class2中的s1运行");
    }
    public void student2() {
        System.out.println("Class2中的s2运行");
    }
}
