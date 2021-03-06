/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.service;

import com.mycompany.bankinterface.crypto.Signer;
import com.mycompany.bankinterface.util.StringUtil;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "QueryContent", urlPatterns = {"/QueryContent"})
public class QueryContent extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(QueryContent.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");

        try (PrintWriter out = response.getWriter()) {
            String data = request.getParameter("data");
            String eid = request.getParameter("eid");

            if (StringUtil.isBlank(data)) {
                writeJsonError("Required parameter -->data<-- not found", out);
                return;
            }

            if (StringUtil.isBlank(eid)) {
                writeJsonError("Required parameter -->eid<-- not found", out);
                return;
            }

            try {
                Map<String, User> users = (Map<String, User>) getServletContext().getAttribute("users");
                EidRecord eidRecord = null;

                for (User user : users.values()) {
                    for (EidRecord e : user.getEidRecords()) {
                        if (e.getEid().equals(eid)) {
                            eidRecord = e;
                        }
                    }
                }

                if (eidRecord == null) {
                    writeJsonError("EID " + eid + " not found", out);
                    return;
                }

                boolean isVerified = false;

                if (StringUtil.hasValue(eidRecord.getVerifierPublicKey())) {
                    logger.info("Checking signed data with public key " + eidRecord.getVerifierPublicKey());
                    Signer signer = (Signer) getServletContext().getAttribute("signer");
                    isVerified = signer.isVerified(data, eidRecord.getSignature(), eidRecord.getVerifierPublicKey());
                }

                JSONObject jo = new JSONObject();
                jo.put("status", ServiceResponseStatus.Ok);
                jo.put("isVerified", isVerified);
                jo.put("signature", eidRecord.getSignature());
                jo.put("signerPublicKey", eidRecord.getVerifierPublicKey());
                jo.put("subjectPublicKey", eidRecord.getSubjectPublicKey());
                jo.put("dataType", eidRecord.getDataType());

                out.write(jo.toString());

            } catch (Exception ex) {
                handleException(ex, out);
            }

        }

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
