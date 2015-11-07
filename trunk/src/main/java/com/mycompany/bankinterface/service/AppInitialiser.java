/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.service;

import com.mycompany.bankinterface.crypto.Signer;
import com.mycompany.bankinterface.crypto.SignerException;
import java.io.File;
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
        String keyStorePath = "/home/philb/mykeystore.jks";

        try {
            File keyStoreFile = new File(keyStorePath);

            logger.info("Creating signer");
            Signer signer = new Signer(keyStoreFile, alias, password);
            servletContext.setAttribute("signer", signer);
        } catch (SignerException sx) {
            logger.error("Failed to create signer", sx);
        }

        logger.info("Application initialised");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application ended");
    }

}
