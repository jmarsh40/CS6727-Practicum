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
public class CISC_ND_000470 extends STIG {
    private String title = "CISC-ND-000470"; // stig ID
    private int cat = 1; // stig category
    private String description = "The Cisco router must be configured to prohibit the use of all unnecessary and nonsecure functions and services.";
    
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
                + "          - no boot network\n"
                + "          - no ip bootp server\n"
                + "          - no ip dns server\n"
                + "          - no ip identd\n"
                + "          - no ip finger\n"
                + "          - no ip http server\n"
                + "          - no ip rcmd rcp-enable\n"
                + "          - no ip rcmd rsh-enable\n"
                + "          - no service config\n"
                + "          - no service finger\n"
                + "          - no service tcp-small-servers\n"
                + "          - no service udp-small-servers\n"
                + "          - no service pad\n"
                + "          - end\n\n";
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
