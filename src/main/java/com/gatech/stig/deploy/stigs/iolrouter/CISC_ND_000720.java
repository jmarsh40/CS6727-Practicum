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
public class CISC_ND_000720 extends STIG {

    private String title = "CISC-ND-000720"; // stig ID
    private int cat = 1;
    private String description = "The Cisco router must be configured to terminate all network connections\n associated with device management after five minutes of inactivity.";
    private String idle = "300"; // idle timout seconds
    private String life = "120"; // http connection life seconds
    private String requests = "100"; // http max requests

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
        String task = "    - name: " + title + " - vty\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - exec-timeout 5 0\n"
                + "        parents:\n"
                + "          - line vty 0 1\n\n"
                + "    - name: " + title + " - console\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - exec-timeout 5 0\n"
                + "        parents:\n"
                + "          - line con 0\n\n"
                + "    - name: " + title + " - timeout\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - ip http timeout-policy idle " + idle + " life " + life + " requests " + requests + "\n\n";
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
            System.out.println("Enter an http idle timout value in seconds (1-300)");
            choice = selector.nextLine();
            try {
                if ((Integer.parseInt(choice) >= 1) && (Integer.parseInt(choice) <= 300)) { // Input is in range
                    idle = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Value not in range");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Value not in range");
            }
        }
        /* Set http timeout */
        while (true) {
            System.out.println("Enter an https connection timout value in seconds (1-86400)");
            choice = selector.nextLine();
            try {
                if ((Integer.parseInt(choice) >= 1) && (Integer.parseInt(choice) <= 86400)) { // Input is in range
                    life = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Value not in range");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Value not in range");
            }
        }
        /* Set max requests */
        while (true) {
            System.out.println("Enter a max number of http connections (1-86400)");
            choice = selector.nextLine();
            try {
                if ((Integer.parseInt(choice) >= 1) && (Integer.parseInt(choice) <= 86400)) { // Input is in range
                    requests = choice;
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
