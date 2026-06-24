/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy;

import java.util.Arrays;

/**
 *
 * @author jmarsh40
 */
public class IP {
    
    /* Return true if input is a valid IP address */
    public static boolean check(String address){
        if ((address == null) || (address.isEmpty())){
            System.out.println("1");
            return false;
        }
        String[] octets = address.split("\\.");
        /* Valid address has 4 octets */
        if (octets.length != 4) {
            System.out.println("2 " + octets.length);
            return false;
        }
        /* Valid octet is between 0 and 255 */
        for (String octet : octets) {
            if ((0 > Integer.parseInt(octet)) || (Integer.parseInt(octet) > 255)) {
                System.out.println("3");
                return false;
            }
        }
        return true;
    }
}
