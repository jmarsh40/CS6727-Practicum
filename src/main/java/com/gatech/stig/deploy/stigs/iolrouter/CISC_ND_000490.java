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
public class CISC_ND_000490 extends STIG {

    private final String title = "CISC-ND-000490"; // stig ID
    private final int cat = 2; // stig category
    private final String description = "The Cisco router must be configured with only one local account to be used as the\n account of last resort in the event the authentication server is unavailable.";
    private String user = "cisco"; // local username
        
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
        String task = "    - name: " + title + " - purge\n"
                + "      cisco.ios.ios_user:\n"
                + "        aggregate:\n"
                + "          - name: " + user + "\n"
                + "        purge: true\n\n"
                + "    - name: " + title + " - aaa\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - aaa new-model\n"
                + "          - aaa authentication login default group tacacs+ local\n\n";
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

        /* Get the local username to keep */
        while (true) {
            System.out.println("Enter the name of the single local user to keep");
            choice = selector.nextLine();
            try {
                if (!choice.isEmpty()) { // Input is not empty
                    user = choice;
                    break;
                } else { // Input is empty
                    System.out.println("Enter a name");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Enter a name");
            }
        }
    }
    
    /* Get STIG Category */
    public int getCat(){
        return cat;
    }
}
