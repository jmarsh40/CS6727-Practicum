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
public class CISC_ND_000460 extends STIG {
    private String title = "CISC-ND-000460"; // stig ID
    private String description = "CAT II - The Cisco router must be configured to limit privileges to change the\n software resident within software libraries.";
    private String scriptText = "file privilege 15";
    
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
