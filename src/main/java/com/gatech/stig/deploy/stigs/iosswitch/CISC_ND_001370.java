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
public class CISC_ND_001370 extends STIG {

    private String title = "CISC-ND-001370"; // stig ID
    private int cat = 2; // stig category
    private String description = "The Cisco switch must be configured to use at least two authentication servers for the\n purpose of authenticating users prior to granting administrative access.";
    private String server1 = "1.1.1.1"; // radius address 1
    private String server2 = "2.2.2.2"; // radius address 2
    private String key1 = "radiuskey1"; // radius key 1
    private String key2 = "radiuskey2"; // radius key 2
        
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
        String task = "    - name: " + title + " - aaa\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - aaa new-model\n\n"
                + "    - name: " + title + " - radius1\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - address ipv4 " + server1 + "\n"
                + "          - key " + key1 + "\n"
                + "        parents:\n"
                + "          - radius server RADIUS1\n\n"
                + "    - name: " + title + " - radius2\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - address ipv4 " + server2 + "\n"
                + "          - key " + key2 + "\n"
                + "        parents:\n"
                + "          - radius server RADIUS2\n\n"
                + "    - name: " + title + " - authentication\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - aaa authentication login CONSOLE group radius local\n"
                + "          - aaa authentication login LOGIN_AUTHENTICATION group radius local\n"
                + "          - ip http authentication aaa login-authentication LOGIN_AUTHENTICATION\n\n"
                + "    - name: " + title + " - vty\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - login authentication LOGIN_AUTHENTICATION\n"
                + "        parents:\n"
                + "          - line vty 0 1\n\n"
                + "    - name: " + title + " - console\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - login authentication CONSOLE\n"
                + "        parents:\n"
                + "          - line con 0\n\n";
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

        /* Set radius server 1 address */
        while (true) {
            System.out.println("Enter an IP address for radius server 1");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    server1 = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
        /* Get the key for radius server 1 */
        while (true) {
            System.out.println("Enter the key for radius server 1");
            choice = selector.nextLine();
            try {
                if (!choice.isEmpty()) { // Input is not empty
                    key1 = choice;
                    break;
                } else { // Input is empty
                    System.out.println("Enter a name");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Enter a name");
            }
        }
         /* Set radius server 2 address */
        while (true) {
            System.out.println("Enter an IP address for radius server 2");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    server2 = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
                /* Get the key for radius server 2 */
        while (true) {
            System.out.println("Enter the key for radius server 2");
            choice = selector.nextLine();
            try {
                if (!choice.isEmpty()) { // Input is not empty
                    key2 = choice;
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
