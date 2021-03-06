/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.service;

import com.mycompany.bankinterface.crypto.Signer;
import com.mycompany.bankinterface.eth.EidProvider;
import com.mycompany.bankinterface.eth.EthereumEidProvider;
import com.mycompany.bankinterface.util.StringUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author philb
 */
@WebServlet(name = "PostVerifiedContent", urlPatterns = {"/PostVerifiedContent"})
public class PostVerifiedContent extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(PostVerifiedContent.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        try (PrintWriter out = response.getWriter()) {

            try {
                String data = request.getParameter("data");
                String dataType = request.getParameter("dataType");
                String subjectPublicKey = request.getParameter("subjectPublicKey");
                String userId = request.getParameter("userId");

                if (StringUtil.isBlank(data)) {
                    writeJsonError("Required parameter -->data<-- not found", out);
                    return;
                }

                if (StringUtil.isBlank(subjectPublicKey)) {
                    writeJsonError("Required parameter -->subjectPublicKey<-- not found", out);
                    return;
                }

                if (StringUtil.isBlank(dataType)) {
                    writeJsonError("Required parameter -->dataType<-- not found", out);
                    return;
                }

                if (StringUtil.isBlank(userId)) {
                    writeJsonError("Required parameter -->userId<-- not found", out);
                    return;

                }

                User user = getUserById(userId);

                if (user == null) {
                    writeJsonError("User " + userId + " not found", out);
                    return;
                }

                EidRecord eidRecord = new EidRecord();

                for (EidRecord r : user.getEidRecords()) {
                    if (r.getDataType().equals(dataType) && r.getData().equals(data)) {
                        eidRecord = r;
                        break;
                    }
                }

                logger.info("Got data -->" + data + "<-- of type -->" + dataType + "<--");

                Signer signer = (Signer) getServletContext().getAttribute("signer");

                if (signer == null) {
                    writeJsonError("Signer not found", out);
                    return;
                }

                String signature = signer.sign(data);

                eidRecord.setSignature(signature);
                eidRecord.setSubjectPublicKey(subjectPublicKey);
                eidRecord.setVerifierPublicKey(signer.getPublicKey());
                eidRecord.setDataType(dataType);
                eidRecord.setData(data);

                EidProvider eidProvider = new EthereumEidProvider();
                String eid = eidProvider.postEidRecord(eidRecord);
                eidRecord.setEid(eid);

                saveUser(user);

                JSONObject jo = new JSONObject();
                jo.put("status", ServiceResponseStatus.Ok);
                jo.put("eid", eidRecord.getEid());
                jo.put("signature", signature);
                jo.put("verifierPublicKey", eidRecord.getVerifierPublicKey());

                out.write(jo.toString());

                // Just for testing, store in the application context
                //getServletContext().setAttribute("eid-" + eid, eidRecord);
            } catch (Exception ex) {
                handleException(ex, out);
            }
        }

    }

    private void saveUser(User user) {

        Map<String, User> users = (Map<String, User>) getServletContext().getAttribute("users");

        if (users == null) {
            users = new HashMap<>();
        }

        users.put(user.getUserId(), user);
    }

    private User getUserById(String userId) {

        Map<String, User> users = (Map<String, User>) getServletContext().getAttribute("users");

        if (users == null) {
            return null;
        }

        return users.get(userId);
    }

    private void writeJsonError(String message, PrintWriter pw) {
        JSONObject jo = new JSONObject();
        jo.put("status", ServiceResponseStatus.Error);
        jo.put("message", message);
        pw.write(jo.toString());

        logger.error("Returning error -->" + message + "<--");
    }

    private void handleException(Exception ex, PrintWriter pw) {
        writeJsonError(ex.getMessage(), pw);
        logger.error("Returning error -->" + ex.getMessage() + "<--", ex);
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
