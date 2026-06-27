/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy.stigs.iosswitch;

import com.gatech.stig.deploy.STIG;
import java.util.Scanner;

/**
 *
 * @author jmarsh40
 */
public class CISC_ND_001440 extends STIG {

    private String title = "CISC-ND-001440"; // stig ID
    private int cat = 2; // stig category
    private String description = "The Cisco switch must be configured to obtain its public key certificates from an appropriate certificate policy through an approved service provider.";
    private String ca = "CA_X"; // certificate authority name
    private String url = "http://trustpoint1.example.com"; // CA enrollment URL
        
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
                + "          - enrollment url " + url + "\n"
                + "        parents:\n"
                + "          - crypto pki trustpoint " + ca + "\n\n";
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

        /* Set snmp group name */
        while (true) {
            System.out.println("Enter a name for the Certificate Authority");
            choice = selector.nextLine();
            try {
                if (!choice.isEmpty()) { // Input is not empty
                    ca = choice;
                    break;
                } else { // Input is empty
                    System.out.println("Enter a name");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Enter a name");
            }
        }
        /* Set snmp read view name */
        while (true) {
            System.out.println("Enter an enrollment URL");
            choice = selector.nextLine();
            try {
                if (!choice.isEmpty()) { // Input is not empty
                    url = choice;
                    break;
                } else { // Input is empty
                    System.out.println("Enter a URL");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Enter a URL");
            }
        }       
    }
    
    /* Get STIG Category */
    public int getCat(){
        return cat;
    }
}
