/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankinterface.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author philb
 */
public class Signer {

    public static final String ALGORITHM = "SHA1withDSA";
    public static final String PROVIDER = "SUN";

    private File keyStoreFile = null;
    private String password = null;
    private KeyStore keyStore = null;
    private KeyPair keyPair = null;
    private String alias = null;

    public Signer(File keyStoreFile, String alias, String password) throws SignerException {
        this.keyStoreFile = keyStoreFile;
        this.password = password;
        this.alias = alias;

        initKeyStore();
        initKeyPair();
    }

    private void initKeyPair() throws SignerException {

        char[] passwordBytes = password.toCharArray();
        Key key;

        try {
            key = keyStore.getKey(alias, passwordBytes);
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            throw new SignerException("Failed to retrieve key", ex);
        }

        if (key instanceof PrivateKey) {
            java.security.cert.Certificate cert;

            try {
                cert = keyStore.getCertificate(alias);
            } catch (KeyStoreException ex) {
                throw new SignerException("Failed to certificate with alias -->" + alias + "<---", ex);

            }

            PublicKey publicKey = cert.getPublicKey();
            keyPair = new KeyPair(publicKey, (PrivateKey) key);
        }
    }

    private void initKeyStore() throws SignerException {

        FileInputStream is = null;

        try {
            is = new FileInputStream(keyStoreFile);
        } catch (FileNotFoundException ex) {
            throw new SignerException("Could not find keystore", ex);
        }

        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        } catch (KeyStoreException ex) {
            throw new SignerException("Could not instantiate keystore", ex);
        }

        char[] passwd = password.toCharArray();

        try {
            keyStore.load(is, passwd);
        } catch (IOException | NoSuchAlgorithmException | CertificateException ex) {
            throw new SignerException("Could not load keystore", ex);
        }

        close(is);
    }

    private void close(FileInputStream is) {

        if (is == null) {
            return;
        }

        try {
            is.close();
        } catch (Exception ex) {
            // log it and move on
        }
    }

    public String sign(String clearText) throws SignerException {
        Signature dsa;

        try {
            dsa = Signature.getInstance(ALGORITHM, PROVIDER);
        } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
            throw new SignerException("Could not get signing instance", ex);
        }

        try {
            dsa.initSign(keyPair.getPrivate());
            dsa.update(clearText.getBytes());

            byte[] signature = dsa.sign();
            String signatureAsString = Base64.encodeBase64String(signature);
            return signatureAsString;

        } catch (InvalidKeyException | SignatureException ex) {
            throw new SignerException("Failed to initialise signing algorithm", ex);
        }
    }
    
    public String getPublicKey() {
        return Base64.encodeBase64String(keyPair.getPublic().getEncoded());
    }
}
