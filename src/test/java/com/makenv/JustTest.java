package com.makenv;

import com.makenv.util.MD5Util;
import org.assertj.core.util.Lists;
import org.junit.Test;

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
}
