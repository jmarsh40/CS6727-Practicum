/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy.stigs.iolrouter;

import com.gatech.stig.deploy.STIG;
import java.util.Scanner;

/**
 *
 * @author jmarsh40
 */
public class CISC_ND_000010 extends STIG {

    private String title = "CISC-ND-000010"; // stig ID
    private int cat = 2;
    private String description = "The Cisco router must be configured to limit the number of concurrent management sessions to an organization-defined number.";
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
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - line vty 0 4\n"
                + "          - session-limit " + sessions + "\n\n";
        return task;
    }

    /* toggle and configure STIG */
    public void configure(boolean en) {
        enable(en);
        if (!en) {
            return;
        }
        /* Configure variables */
        Scanner selector = new Scanner(System.in);
        String choice = "";

        /* Set idle timeout */
        while (true) {
            System.out.println("Enter the max number of concurrent management sessions (0-4294967295)");
            choice = selector.nextLine();
            try {
                if ((Integer.parseInt(choice) >= 0)) { // Input is in range
                    sessions = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Value not in range");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Value not in range");
            }
        }
    }
    
    /* Get STIG Category */
    public int getCat(){
        return cat;
    }
}
