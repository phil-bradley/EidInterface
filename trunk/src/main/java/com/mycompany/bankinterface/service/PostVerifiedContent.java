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

        try (PrintWriter out = response.getWriter()) {

            try {
                String data = request.getParameter("data");
                String subjectPublicKey = request.getParameter("subjectPublicKey");

                if (StringUtil.isBlank(data)) {
                    writeJsonError("Required parameter -->data<-- not found", out);
                    return;
                }

                if (StringUtil.isBlank(subjectPublicKey)) {
                    writeJsonError("Required parameter -->subjectPublicKey<-- not found", out);
                    return;
                }

                Signer signer = (Signer) getServletContext().getAttribute("signer");
                String signature = signer.sign(data);

                String eid = StringUtil.randomAlphaNum(32);

                EidRecord eidRecord = new EidRecord();
                eidRecord.setEid(eid);
                eidRecord.setSignature(signature);
                eidRecord.setSubjectPublicKey(subjectPublicKey);
                eidRecord.setVerifierPublicKey(signer.getPublicKey());


                JSONObject jo = new JSONObject();
                jo.put("status", ServiceResponseStatus.Ok);
                jo.put("eid", eidRecord.getEid());
                jo.put("signature", signature);
                jo.put("verifierPublicKey", eidRecord.getVerifierPublicKey());

                out.write(jo.toString());
                
                // Just for testing, store in the application context
                getServletContext().setAttribute(eid, eidRecord);

            } catch (Exception ex) {
                writeJsonError(ex.getMessage(), out);
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
