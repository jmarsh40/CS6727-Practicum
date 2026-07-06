/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy.stigs.iosswitch;

import com.gatech.stig.deploy.IP;
import com.gatech.stig.deploy.STIG;
import java.util.Scanner;

/**
 *
 * @author jmarsh40
 */
public class CISC_ND_001150 extends STIG {

    private final String title = "CISC-ND-001150"; // stig ID
    private final int cat = 2; // stig category
    private final String description = "The Cisco switch must be configured to authenticate Network Time Protocol (NTP) sources\n using authentication with FIPS-compliant algorithms.";
    private String ntp1 = "1.1.1.1"; // NTP primary server host
    private String ntp2 = "2.2.2.2"; // NTP secondary host
    private String key = "ntpkey"; // NTP authentication key
        
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
                + "          - ntp authentication-key 1 md5 "+ key + "\n"
                + "          - ntp authenticate\n"
                + "          - ntp trusted-key 1\n"
                + "          - ntp server " + ntp1 + " key 1\n"
                + "          - ntp server " + ntp2 + " key 1\n\n";
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

        /* Set ntp authentication key */
        while (true) {
            System.out.println("Enter an NTP authentication key");
            choice = selector.nextLine();
            try {
                if (!choice.isEmpty()) { // Input is not empty
                    key = choice;
                    break;
                } else { // Input is empty
                    System.out.println("Enter a key");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Value not in range");
            }
        }
        /* Set primary NTP server address */
        while (true) {
            System.out.println("Enter an IP address for primary NTP server");
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
        /* Set secondary NTP server address */
        while (true) {
            System.out.println("Enter an IP address for secondary NTP server");
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
