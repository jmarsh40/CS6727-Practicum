/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy.stigs.iosswitch;

import com.gatech.stig.deploy.STIG;

/**
 *
 * @author jmarsh40
 */
public class CISC_ND_000550 extends STIG {
    private final String title = "CISC-ND-000550"; // stig ID
    private final int cat = 2; // stig category
    private final String description = "The Cisco switch must be configured to enforce a minimum 15-character password length.";
    
    /* Return STIG info */
    public String getInfo(){
        /* Get roman numeral for STIG category and assemble output*/
        String c = "Uncategorized";
        switch(cat) {
            case 1:
                c = "I";
                break;
            case 2:
                c = "II";
                break;
            case 3:
                c = "III";
        }
        String info = "STIG Category and ID: " + c + " - " + title + "\n"
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
                + "          - min-length 15\n"
                + "        parents:\n"
                + "          - aaa common-criteria policy PASSWORD_POLICY\n\n";
        return task;
    }
    
    /* toggle and configure STIG */
    public void configure(boolean en) {
        enable(en);
    }
    
    /* Get STIG Category */
    public int getCat(){
        return cat;
    }
}
