/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy.stigs.router;

import com.gatech.stig.deploy.STIG;

/**
 *
 * @author jmarsh40
 */
public class CISC_ND_000280 extends STIG {
    private String title = "CISC-ND-000280"; // stig ID
    private String description = "CAT II - The Cisco router must produce audit records containing information to establish when (date and time) the\n events occurred.";
    private String scriptText = "service timestamps log datetime localtime";
    
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
        String task = "    - name: " + title + "\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - " + scriptText + "\n\n";
        enable(true);
        return task;
    }
}
