/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
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
@WebServlet(name = "ListEid", urlPatterns = {"/ListEid"})
public class ListEid extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            Enumeration<String> attributeNames = getServletContext().getAttributeNames();
            List<EidRecord> eidRecords = new ArrayList<EidRecord>();

            while (attributeNames.hasMoreElements()) {
                String name = attributeNames.nextElement();

                if (!name.startsWith("eid")) {
                    continue;
                }

                EidRecord eidRecord = (EidRecord) getServletContext().getAttribute(name);
                eidRecords.add(eidRecord);

            }

            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            for (EidRecord eidRecord : eidRecords) {
                jsonArrayBuilder.add(
                        Json.createObjectBuilder()
                        .add("eid", eidRecord.getEid())
                        .add("dataType", eidRecord.getDataType())
                        .add("data", eidRecord.getData())
                );
            }

            JsonArray ediRecordsJson = jsonArrayBuilder.build();
            out.write(ediRecordsJson.toString());
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
