/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author philb
 */
public class User {

    private String userId;
    private String name;
    private List<EidRecord> eidRecords = new ArrayList<>();

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EidRecord> getEidRecords() {
        return eidRecords;
    }

}
