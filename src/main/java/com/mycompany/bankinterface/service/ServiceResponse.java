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
public class ServiceResponse {

    private ServiceResponseStatus status;
    private String message = "";
    private String eid = "";
    private String signature = "";
    private String verifierPublicKey = "";

    public ServiceResponse(ServiceResponseStatus status) {
        this(status, "");
    }

    public ServiceResponse(ServiceResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ServiceResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceResponseStatus status) {
        this.status = status;
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
