package com.tester.extend;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * @ClassName TestMethodsDemo
 * @Description TODO
 * @Author lixinwei
 * @Date 2021/8/22 1:11 下午
 * @Version 1.0
 **/
public class TestMethodsDemo {
    @Test
    public void test1() {
        Assert.assertEquals(1,2);
    }

    @Test
    public void test2() {
        Assert.assertEquals(1,1);
    }

    @Test
    public void test3() {
        Assert.assertEquals("aaa","aaa");
    }

    @Test
    public void logDemo() {
        Reporter.log("这是myself日志");
        throw new RuntimeException("这是我自己运行时异常");
    }
}
