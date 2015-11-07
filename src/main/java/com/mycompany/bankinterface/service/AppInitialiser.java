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

        User user1 = new User("0001", "Bob");
        User user2 = new User("0002", "Fred");
        User user3 = new User("0003", "Jack");
        User user4 = new User("0004", "Tim");

        EidRecord phoneRecord = new EidRecord();
        phoneRecord.setData("+353 123456");
        phoneRecord.setDataType("PHONE");
        phoneRecord.setEid("ABCD1234WXYZ");
        
        EidRecord passportRecord = new EidRecord();
        passportRecord.setData("PA123456");
        passportRecord.setDataType("PASSPORT");

        user1.getEidRecords().add(phoneRecord);
        user1.getEidRecords().add(passportRecord);

        Map<String, User> users = new HashMap<>();

        users.put(user1.getUserId(), user1);
        users.put(user2.getUserId(), user2);
        users.put(user3.getUserId(), user3);
        users.put(user4.getUserId(), user4);

        servletContext.setAttribute("users", users);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application ended");
    }

}
