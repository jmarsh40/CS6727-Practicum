/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy.stigs.router;

import com.gatech.stig.deploy.STIG;

/**
 *
 * @author Ragnarak
 */
public class CISC_ND_000150 extends STIG {
    private String title = "CISC-ND-000150"; // stig ID
    private String description = "The Cisco router must be configured to enforce the limit of three consecutive invalid logon attempts, \nafter which time it must lock out the user account from accessing the device for 15 minutes.";
    private String scriptText = "login block-for 900 attempts 3 within 120";
    
    /* Return STIG info */
    public String getInfo(){
        /* TBD */
        String info = "STIG ID: " + title + "\n"
                + "Description: " + description + "\n"
                + "Enabled: " + String.valueOf(enabled);
        return info;
    }
    
    /* return the text of the script to be written to the ansible playbook */
    public String apply(){
        /* Build task script */
        String task = "- name: " + title + "\n"
                + "  cisco.ios.ios_config:\n"
                + "    lines:\n"
                + "      - " + scriptText;
        enable(true);
        System.out.println("!!!Applied STIG!!!\n");
        return task;
    }
}
