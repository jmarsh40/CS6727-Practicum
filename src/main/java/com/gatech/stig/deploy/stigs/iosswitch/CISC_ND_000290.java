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
public class CISC_ND_000290 extends STIG {

    private String title = "CISC-ND-000290"; // stig ID
    private int cat = 2;
    private String description = "The Cisco switch must produce audit records containing information to establish where the events occurred.";
    private String sessions = "2"; // idle concurrent sessions

    /* Return STIG info */
    public String getInfo() {
        /* Get roman numeral for STIG category and assemble output*/
        String c = "Uncategorized";
        switch (cat) {
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
    public String apply() {

        /* Build task script */
        String task = "    - name: " + title + "\n"
                + "      cisco.ios.ios_acls:\n"
                + "        config:\n"
                + "          - afi: ipv4\n"
                + "            acls:\n"
                + "              - name: BLOCK_INBOUND\n"
                + "                acl_type: extended\n"
                + "                aces:\n"
                + "                  - grant: deny\n"
                + "                    protocol: icmp\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                    log_input:\n"
                + "                      set: true\n"
                + "        state: replaced\n\n";
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
