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
public class CISC_ND_000140 extends STIG {

    private String title = "CISC-ND-000140"; // stig ID
    private int cat = 2;
    private String description = "The Cisco router must be configured to enforce approved authorizations for controlling the\n flow of management information within the device based on control policies.";
    private String address = "192.168.1.0"; // management subnet address
    private String mask = "0.0.0.255"; // management reverse mask

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
        String task = "    - name: " + title + " - acl\n"
                + "      cisco.ios.ios_acls:\n"
                + "        config:\n"
                + "          - afi: ipv4\n"
                + "            acls:\n"
                + "              - name: MANAGEMENT_NET\n"
                + "                acl_type: standard\n"
                + "                aces:\n"
                + "                  - grant: permit\n"
                + "                    source:\n"
                + "                      address: " + address + "\n"
                + "                      wildcard_bits: " + mask + "\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                  - grant: deny\n"
                + "                    protocol: icmp\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                    log_input:\n"
                + "                      set: true\n"
                + "        state: replaced\n\n"
                + "    - name: " + title + " - vty\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - transport input ssh\n"
                + "          - access-class MANAGEMENT_NET in\n"
                + "        parents:\n"
                + "          - line vty 0 1\n\n";
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

        /* Set management subnet address */
        while (true) {
            System.out.println("Enter an IP address for the management subnet");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    address = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
        /* Set management reverse mask */
        while (true) {
            System.out.println("Enter a reverse mask for the management subnet");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    mask = choice;
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
