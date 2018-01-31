package com.makenv;

import com.makenv.util.MD5Util;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class JustTest {

    @Test
    public void getPassword() {
        System.out.println(MD5Util.MD5EncodeUtf8("123"));
    }


    @Test
    public void testNull() {
        List<String> list = Lists.newArrayList();
        for (String str : list) {
            System.out.println(str);
        }
    }

    @Test
    public void testList() {
        List<String> stringList = Lists.newArrayList();
        stringList.add("1");
        stringList.add("2");
        stringList.add("3");
        System.out.println(stringList);
    }

    @Test
    public void testNull2() {
        String str = null;
        if (str == null || str.charAt(0) == 'a') {
            System.out.println("hehe");
        }
    }

    @Test
    public void testRandom() {
        //应该是生成小于100的随机数
        System.out.println(new Random().nextInt(100));
    }
}
