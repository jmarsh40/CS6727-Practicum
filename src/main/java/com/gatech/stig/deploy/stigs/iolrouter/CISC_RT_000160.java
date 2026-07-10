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
public class CISC_RT_000160 extends STIG {

    private final String title = "CISC-RT-000160"; // stig ID
    private final int cat = 2;
    private final String description = "The Cisco router must be configured to have IP directed broadcast disabled on all interfaces.";
    private String inf = "g0/1"; // interface to apply setting

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
                + "          - no ip directed-broadcast\n"
                + "        parents:\n"
                + "          - " + inf + "\n\n";
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

        /* Set target interface */
        while (true) {
            System.out.println("Enter the interface to disable directed broadcast");
            choice = selector.nextLine();
            try {
                if ((!choice.isEmpty())) { // Input is not empty
                    inf = choice;
                    break;
                } else { // Input is empty
                    System.out.println("Enter an interface");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Enter an interface");
            }
        }
    }
    
    /* Get STIG Category */
    public int getCat(){
        return cat;
    }
}
