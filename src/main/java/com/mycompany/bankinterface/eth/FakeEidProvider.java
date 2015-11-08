/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.eth;

import com.mycompany.bankinterface.service.EidRecord;
import com.mycompany.bankinterface.util.StringUtil;

/**
 *
 * @author philb
 */
public class FakeEidProvider implements EidProvider {

    @Override
    public EidRecord getEidRecord(String eid) {
        return null;
    }

    @Override
    public String postEidRecord(EidRecord record) {
        String eid = StringUtil.randomAlphaNum(32);
        return eid;
    }
}
