/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy.stigs.iolrouter;

import com.gatech.stig.deploy.IP;
import com.gatech.stig.deploy.STIG;
import java.util.Scanner;

/**
 *
 * @author jmarsh40
 */
public class CISC_ND_001030 extends STIG {

    private final String title = "CISC-ND-001030"; // stig ID
    private final int cat = 2;
    private final String description = "The Cisco router must be configured to synchronize its clock with the primary\n and secondary time sources using redundant authoritative time sources.";
    private String ntp1 = "1.1.1.1"; // ntp server 1
    private String ntp2 = "2.2.2.2"; // ntp server 2
        
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
                + "          - ntp server " + ntp1 + "\n"
                + "          - ntp server " + ntp2 + "\n\n";
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

        /* Set npt1 address */
        while (true) {
            System.out.println("Enter an IP address for the primary NTP server");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    ntp1 = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
        /* Set npt1 address */
        while (true) {
            System.out.println("Enter an IP address for the secondary NTP server");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    ntp2 = choice;
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
