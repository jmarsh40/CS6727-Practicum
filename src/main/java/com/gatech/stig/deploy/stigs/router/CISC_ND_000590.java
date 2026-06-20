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
public class CISC_ND_000590 extends STIG {
    private String title = "CISC-ND-000590"; // stig ID
    private String description = "CAT II - The Cisco router must be configured to enforce password complexity by requiring that\n at least one numeric character be used.";
    private String scriptText1 = "aaa common-criteria policy PASSWORD_POLICY";
    private String scriptText2 = "numeric-count 1";
    private String scriptText3 = "exit";
    
    /* Return STIG info */
    public String getInfo(){
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
                + "          - " + scriptText1 + "\n"
                + "          - " + scriptText2 + "\n"
                + "          - " + scriptText3 + "\n\n";
        enable(true);
        return task;
    }
}
