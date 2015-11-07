/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.util;

/**
 *
 * @author philb
 */
public class StringUtil {

    public static boolean isBlank(String s) {
        if (s == null) {
            return false;
        }

        return s.trim().isEmpty();
    }
    
    public static boolean hasValue(String s) {
        return !isBlank(s);
    }
}
