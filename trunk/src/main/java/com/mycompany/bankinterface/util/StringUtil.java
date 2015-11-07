/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.util;

import java.util.Random;

/**
 *
 * @author philb
 */
public class StringUtil {

    public static final String ALPHANUMS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static final String randomAlphaNum(int length) {
        Random r = new Random(System.currentTimeMillis());

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int idx = r.nextInt(ALPHANUMS.length());
            char c = ALPHANUMS.charAt(idx);
            sb.append(c);
        }
        
        return sb.toString();
    }

    public static boolean isBlank(String s) {
        if (s == null) {
            return true;
        }

        return s.trim().isEmpty();
    }

    public static boolean hasValue(String s) {
        return !isBlank(s);
    }
}
