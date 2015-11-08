/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.eth;

import com.mycompany.bankinterface.service.EidRecord;

/**
 *
 * @author philb
 */
public interface EidProvider {

    EidRecord getEidRecord(String eid) throws EidException;

    String postEidRecord(EidRecord record) throws EidException;
}
