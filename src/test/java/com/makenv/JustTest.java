package com.makenv;

import com.makenv.util.MD5Util;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

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
}
