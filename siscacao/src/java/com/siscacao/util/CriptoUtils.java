/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.util;

/**
 *
 * @author Hanzo
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CriptoUtils {

    public static String MD5Digest(String value) {

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(Integer.toHexString((int) (b & 0xff)));
            }
//para debug
            System.out.println("original:" + value);
            System.out.println("digested:" + digest);
            System.out.println("digested(hex):" + sb.toString());
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CriptoUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }



    }
}
