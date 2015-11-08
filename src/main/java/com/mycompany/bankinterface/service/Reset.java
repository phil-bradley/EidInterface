/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.service;

import com.mycompany.bankinterface.crypto.Signer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author philb
 */
@WebServlet(name = "Reset", urlPatterns = {"/Reset"})
public class Reset extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            Signer signer = (Signer) getServletContext().getAttribute("signer");

            User user1 = new User("0001", "Erikos");
            User user2 = new User("0002", "Fred");
            User user3 = new User("0003", "Jack");
            User user4 = new User("0004", "Tim");

            EidRecord fnameRecord = new EidRecord();
            fnameRecord.setData("Erikos");
            fnameRecord.setDataType("FIRSTNAME");
            fnameRecord.setEid("HSKE23445JLLM993");
            fnameRecord.setVerifierPublicKey(signer.getPublicKey());
            fnameRecord.setSignature(signer.sign(fnameRecord.getData()));
            user1.getEidRecords().add(fnameRecord);

            EidRecord lnameRecord = new EidRecord();
            lnameRecord.setData("Alkalai");
            lnameRecord.setDataType("LASTNAME");
            lnameRecord.setEid("BV73945454DSAA");
            lnameRecord.setVerifierPublicKey(signer.getPublicKey());
            lnameRecord.setSignature(signer.sign(lnameRecord.getData()));
            user1.getEidRecords().add(lnameRecord);

            EidRecord passportRecord = new EidRecord();
            passportRecord.setData("XX575245");
            passportRecord.setDataType("PASSPORT");
            passportRecord.setEid("YTENVP3424222XZW");
            passportRecord.setSignature(signer.sign(passportRecord.getData()));
            passportRecord.setVerifierPublicKey(signer.getPublicKey());

            user1.getEidRecords().add(passportRecord);

            EidRecord drivingRecord = new EidRecord();
            drivingRecord.setData("AA456732");
            drivingRecord.setDataType("DRIVINGLICENSE");
            // No EID
            user1.getEidRecords().add(drivingRecord);

            EidRecord addressRecord = new EidRecord();
            addressRecord.setData("Van troelaan 42, 4563FF, Amsterdam, The Netherlands");
            addressRecord.setDataType("ADDRESS");
            // No EID
            user1.getEidRecords().add(addressRecord);

            EidRecord dobRecord = new EidRecord();
            dobRecord.setData("23-05-1985");
            dobRecord.setDataType("DATEOFBIRTH");
            dobRecord.setEid("PODRNVJ82345665FG");
            dobRecord.setVerifierPublicKey(signer.getPublicKey());
            dobRecord.setSignature(signer.sign(dobRecord.getData()));

            user1.getEidRecords().add(dobRecord);

            Map<String, User> users = new HashMap<>();

            users.put(user1.getUserId(), user1);
            users.put(user2.getUserId(), user2);
            users.put(user3.getUserId(), user3);
            users.put(user4.getUserId(), user4);

            getServletContext().setAttribute("users", users);

            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Reset</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Reset at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {

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
