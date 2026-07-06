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
public class CISC_ND_001450 extends STIG {

    private final String title = "CISC-ND-001450"; // stig ID
    private final int cat = 1;
    private final String description = "The Cisco router must be configured to send log data to at least two syslog servers\n for the purpose of forwarding alerts to the administrators and the ISSO.";
    private String sysLog1 = "1.1.1.1"; // syslog server 1
    private String sysLog2 = "2.2.2.2"; // syslog server 2
        
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
                + "          - logging host " + sysLog1 + "\n"
                + "          - logging host " + sysLog2 + "\n\n";
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

        /* Set syslog 1 address */
        while (true) {
            System.out.println("Enter an IP address for the first syslog server");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    sysLog1 = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
        /* Set syslog 2 address */
        while (true) {
            System.out.println("Enter an IP address for the second syslog server");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    sysLog2 = choice;
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
