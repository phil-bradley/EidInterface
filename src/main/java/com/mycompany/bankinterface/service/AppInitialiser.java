/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.service;

import com.mycompany.bankinterface.crypto.Signer;
import com.mycompany.bankinterface.crypto.SignerException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author philb
 */
@WebListener
public class AppInitialiser implements ServletContextListener {

    public static final Logger logger = LoggerFactory.getLogger(AppInitialiser.class);

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("Starting application");
        this.servletContext = servletContextEvent.getServletContext();

        String alias = "philipbradleydsa";
        String password = "password";
        String keyStorePath = "/tmp/mykeystore.jks";

        try {
            File keyStoreFile = new File(keyStorePath);

            logger.info("Creating signer");
            Signer signer = new Signer(keyStoreFile, alias, password);
            servletContext.setAttribute("signer", signer);
        } catch (SignerException sx) {
            logger.error("Failed to create signer", sx);
        }

        initTestData();

        logger.info("Application initialised");
    }

    private void initTestData() {

        User user1 = new User("0001", "Erikos");
        User user2 = new User("0002", "Fred");
        User user3 = new User("0003", "Jack");
        User user4 = new User("0004", "Tim");

        EidRecord fnameRecord = new EidRecord();
        fnameRecord.setData("Erikos");
        fnameRecord.setDataType("FIRSTNAME");
        fnameRecord.setEid("HSKE23445JLLM993");
        user1.getEidRecords().add(fnameRecord);

        EidRecord lnameRecord = new EidRecord();
        lnameRecord.setData("Alkalai");
        lnameRecord.setDataType("LASTNAME");
        lnameRecord.setEid("BV73945454DSAA");
        user1.getEidRecords().add(lnameRecord);

        EidRecord passportRecord = new EidRecord();
        passportRecord.setData("XX575245");
        passportRecord.setDataType("PASSPORT");
        passportRecord.setEid("YTENVP3424222XZW");
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
        user1.getEidRecords().add(dobRecord);

        Map<String, User> users = new HashMap<>();

        users.put(user1.getUserId(), user1);
        users.put(user2.getUserId(), user2);
        users.put(user3.getUserId(), user3);
        users.put(user4.getUserId(), user4);

        servletContext.setAttribute("users", users);
        servletContext.setAttribute("defaultUsers", users);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application ended");
    }

}
