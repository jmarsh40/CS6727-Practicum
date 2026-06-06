/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy;

/**
 *
 * @author Ragnarak
 */
public class ACL {
    private static String name; // access control list name
    private static String[] rList; // list of rules in ACL
    
    /* Return the ACL name */
    public static String getName() {
        return name;
    }
    /* Return ACL rule list */
    public static String getACL() {
        return String.join("\n", rList);
    }
}
