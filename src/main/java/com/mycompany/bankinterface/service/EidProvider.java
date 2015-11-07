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
public class EidProvider {

    public EidProvider() {

    }

    public EidRecord getEidRecord(String eid) {
        return null;
    }
    
    public EidRecord getFakeEidRecord(String eid) {
        EidRecord eidRecord = new EidRecord();
        eidRecord.setEid("ABCDEFGHIJKLMNOP");
        eidRecord.setSignature("sdjfksdfkghdfgkldfgdfg");
        eidRecord.setVerifierPublicKey("m,nvkbnklosdfhsdfsdfla;lkasdasdfkjsdf");
        
        return eidRecord;
    }
}
