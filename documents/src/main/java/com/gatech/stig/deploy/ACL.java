/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy;

/**
 *
 * @author jmarsh40
 */
public class ACL {
    private String name; // access control list name
    private String[] rList; // list of rules in ACL
    
    /* Return the ACL name */
    public String getName() {
        return name;
    }
    /* Return ACL rule list */
    public String getACL() {
        return String.join("\n", rList);
    }
}
