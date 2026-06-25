/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy.stigs.router;

import com.gatech.stig.deploy.IP;
import com.gatech.stig.deploy.STIG;
import java.util.Scanner;

/**
 *
 * @author jmarsh40
 */
public class CISC_ND_001410 extends STIG {

    private String title = "CISC-ND-001410"; // stig ID
    private int cat = 2; // stig category
    private String description = "The Cisco router must be configured to authenticate SNMP messages using a\n FIPS-validated Keyed-Hash Message Authentication Code (HMAC).";
    private String server = "1.1.1.1"; // SCP backup server
        
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
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - event syslog pattern \"%SYS-5-CONFIG_I\"\n"
                + "          - action 1 cli command \"enable\"\n"
                + "          - action 2 info type routername\n"
                + "          - action 3 cli command \"copy run scp\" pattern \"remote host\"\n"
                + "          - action 4 cli command \"" + server + "\" pattern \"filename\"\n"
                + "          - action 5 cli command \"$_info_routername-config\"\n"
                + "          - action 6 syslog priority informational msg \"Configuration backup was executed\"\n"
                + "        parents:\n"
                + "          - event manager applet BACKUP_CONFIG\n\n";
        return task;
    }

    /* toggle and configure STIG */
    public void configure(boolean en) {
        enable(en);
        if (!en) {
            return;
        }
        Scanner selector = new Scanner(System.in);
        String choice = "";

        /* Set SCP backup server address */
        while (true) {
            System.out.println("Enter an IP address for the SCP backup server");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    server = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
    }
    
    /* Get STIG Category */
    public int getCat(){
        return cat;
    }
}
