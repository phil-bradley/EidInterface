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
    private String signedData;
    private String verifierPublicKey;

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getSignedData() {
        return signedData;
    }

    public void setSignedData(String signedData) {
        this.signedData = signedData;
    }

    public String getVerifierPublicKey() {
        return verifierPublicKey;
    }

    public void setVerifierPublicKey(String verifierPublicKey) {
        this.verifierPublicKey = verifierPublicKey;
    }

}
