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
public class CISC_ND_001130 extends STIG {

    private String title = "CISC-ND-001130"; // stig ID
    private int cat = 2; // stig category
    private String description = "The Cisco router must be configured to authenticate SNMP messages using a\n FIPS-validated Keyed-Hash Message Authentication Code (HMAC).";
    private String group = "V3GROUP"; // SNMP group name
    private String read = "V3READ"; // SNMP read name
    private String write = "V3WRITE"; // SNMP write name
    private String user = "V3USER"; // SNMP user name
    private String pass = "password"; // SNMP user password
    private String host = "1.1.1.1"; // SNMP server host
        
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
                + "          - snmp-server group " + group + " v3 auth read " + read + " write " + write + "\n"
                + "          - snmp-server user " + user + " " + group + " v3 auth sha " + pass + "\n"
                + "          - snmp-server view " + read + " iso included\n"
                + "          - snmp-server view " + write + " iso included\n"
                + "          - snmp-server host " + host + " version 3 auth " + user + "\n\n";
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
            System.out.println("Enter a name for the snmp group");
            choice = selector.nextLine();
            try {
                if (!choice.isEmpty()) { // Input is not empty
                    group = choice;
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
            System.out.println("Enter a name for the snmp read view");
            choice = selector.nextLine();
            try {
                if (!choice.isEmpty()) { // Input is not empty
                    read = choice;
                    break;
                } else { // Input is empty
                    System.out.println("Enter a name");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Enter a name");
            }
        }
        /* Set snmp write view name */
        while (true) {
            System.out.println("Enter a name for the snmp write view");
            choice = selector.nextLine();
            try {
                if (!choice.isEmpty()) { // Input is not empty
                    write = choice;
                    break;
                } else { // Input is empty
                    System.out.println("Enter a name");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Enter a name");
            }
        }
        /* Set snmp user name */
        while (true) {
            System.out.println("Enter a name for the snmp user");
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
        /* Set snmp user password */
        while (true) {
            System.out.println("Enter a password for the SNMP user V3USER");
            choice = selector.nextLine();
            try {
                if (!choice.isEmpty()) { // Input is not empty
                    pass = choice;
                    break;
                } else { // Input is empty
                    System.out.println("Enter a password");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Value not in range");
            }
        }
        /* Set snmp server address */
        while (true) {
            System.out.println("Enter an IP address for the snmp server host");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    host = choice;
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
