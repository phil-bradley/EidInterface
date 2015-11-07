/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author philb
 */
@WebServlet(name = "ListUsers", urlPatterns = {"/ListUsers"})
public class ListUsers extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            List<User> users = (List<User>) getServletContext().getAttribute("users");
            
            if (users == null) {
                //logger.info("No users found in application context");
                users = new ArrayList<>();
            }
            
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            for (User user : users) {
                jsonArrayBuilder.add(
                        Json.createObjectBuilder()
                        .add("userId", user.getUserId())
                        .add("name", user.getName())
                );
            }

            JsonArray userJsonArray = jsonArrayBuilder.build();
            out.write(userJsonArray.toString());

//            Enumeration<String> attributeNames = getServletContext().getAttributeNames();
//            java.util.List<EidRecord> eidRecords = new ArrayList<>();
//
//            JSONObject jo = new JSONObject();
//
//            while (attributeNames.hasMoreElements()) {
//                String name = attributeNames.nextElement();
//
//                if (!name.startsWith("eid")) {
//                    continue;
//                }
//
//                EidRecord eidRecord = (EidRecord) getServletContext().getAttribute(name);
//                eidRecords.add(eidRecord);
//
//                jo.put(eidRecord.getDataType(),
//                        Json.createObjectBuilder()
//                        .add("eid", eidRecord.getEid())
//                        .add("data", eidRecord.getData())
//                );
//        }
//            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
//            for (EidRecord eidRecord : eidRecords) {
//                jsonArrayBuilder.add(
//                        Json.createObjectBuilder()
//                        .add("eid", eidRecord.getEid())
//                        .add("dataType", eidRecord.getDataType())
//                        .add("data", eidRecord.getData())
//                );
//            }
//
//            JsonArray ediRecordsJson = jsonArrayBuilder.build();//
            //
            //
        }
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
