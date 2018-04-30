package com.gjf.utils;

import org.junit.Test;

/**
 * @Author: GJF
 * @Date : 2018/04/30
 * Time   : 22:56
 */
public class EncryptKitTest {
    @Test
    public void testEncrypt(){
        System.out.println(EncryptKit.md5("username","haha"));
    }
}
