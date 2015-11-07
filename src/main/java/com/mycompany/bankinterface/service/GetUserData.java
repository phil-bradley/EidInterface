/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.service;

import com.mycompany.bankinterface.util.StringUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletContext;
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
@WebServlet(name = "GetUserData", urlPatterns = {"/GetUserData"})
public class GetUserData extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(GetUserData.class);

    private ServletContext servletContext;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        servletContext = request.getServletContext();

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        try (PrintWriter out = response.getWriter()) {

            String userId = request.getParameter("userId");

            if (StringUtil.isBlank(userId)) {
                writeJsonError("Required parameter -->userId<-- not found", out);
                return;
            }

            User user = getUserById(userId);

            if (user == null) {
                writeJsonError("User " + userId + " not found", out);
                return;
            }

            /*
             JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

             for (EidRecord eidRecord : user.getEidRecords()) {
             jsonArrayBuilder.add(
             Json.createObjectBuilder()
             .add("eid", eidRecord.getEid())
             .add("dataType", eidRecord.getDataType())
             .add("data", eidRecord.getData())
             );
             }

             JsonArray ediRecordsJson = jsonArrayBuilder.build();
             */
            JSONObject hash = new JSONObject();
            
            for (EidRecord eidRecord : user.getEidRecords()) {
                JSONObject jo = new JSONObject();
                jo.put("data", eidRecord.getData());
                jo.put("eid", eidRecord.getEid());            
                hash.put(eidRecord.getDataType(), jo);
            }

            out.write(hash.toString());
        }
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
