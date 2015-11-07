/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.service;

/**
 *
 * @author philb
 */
public class EidRecord {

    private String eid;
    private String signature;
    private String verifierPublicKey;
    private String subjectPublicKey;

    public String getSubjectPublicKey() {
        return subjectPublicKey;
    }

    public void setSubjectPublicKey(String subjectPublicKey) {
        this.subjectPublicKey = subjectPublicKey;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getVerifierPublicKey() {
        return verifierPublicKey;
    }

    public void setVerifierPublicKey(String verifierPublicKey) {
        this.verifierPublicKey = verifierPublicKey;
    }

}
