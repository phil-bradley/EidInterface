/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.crypto;

/**
 *
 * @author philb
 */
public class SignerException extends Exception {

    public SignerException(String message, Throwable t) {
        super(message, t);
    }
}
