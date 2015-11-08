/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.eth;

import com.mycompany.bankinterface.service.EidRecord;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 *
 * @author philb
 */
public class EthereumEidProvider implements EidProvider {

    private static final String dest = "http://159.122.225.36:3001/v1/evid";

    @Override
    public EidRecord getEidRecord(String eid) throws EidException {
        String queryLocation = dest + "?eid=" + eid;

        try {
            String response = sendGetMessage(queryLocation);
            JSONObject responseJo = new JSONObject(response);
            JSONObject dataJo = (JSONObject) responseJo.get("data");

            EidRecord responseRecord = new EidRecord();
            responseRecord.setEid(eid);
            responseRecord.setSignature(dataJo.getString("signature"));
            responseRecord.setVerifierPublicKey(dataJo.getString("verifier_pk"));

            return responseRecord;
        } catch (IOException x) {
            throw new EidException("Failed to retrieve eid record", x);
        }
    }

    @Override
    public String postEidRecord(EidRecord record) throws EidException {
        JSONObject jo = createRequestObject(record);

        try {
            String response = postJsonMessage(jo);
            return response;
        } catch (Exception ex) {
            throw new EidException("Failed to post request", ex);
        }
    }

    private String sendGetMessage(String location) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(location);

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            String content = IOUtils.toString(entity.getContent());
            EntityUtils.consume(entity);
            return content;
        }

    }

    private String postJsonMessage(JSONObject jo) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(dest);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(jo.toString(), "UTF-8"));

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            String content = IOUtils.toString(entity.getContent());
            EntityUtils.consume(entity);
            return content;
        }
    }

    private JSONObject createRequestObject(EidRecord record) {
        JSONObject jo = new JSONObject();
        jo.put("signature", record.getSignature());
        jo.put("verifier_pk", record.getVerifierPublicKey());

        JSONObject requestJo = new JSONObject();
        requestJo.put("request", jo);

        return requestJo;
    }

}
