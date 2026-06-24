/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy.stigs.router;

import com.gatech.stig.deploy.STIG;
import java.util.Scanner;

/**
 *
 * @author jmarsh40
 */
public class CISC_ND_000980 extends STIG {
    private String title = "CISC-ND-000980"; // stig ID
    private int cat = 2;
    private String description = "The Cisco router must be configured to allocate audit record storage capacity \nin accordance with organization-defined audit record storage requirements.";
    private String buffer = "4096"; // idle timout seconds
    private String level = "3"; // logging level
        
    /* Return STIG info */
    public String getInfo(){
        /* Get roman numeral for STIG category and assemble output*/
        String c = "Uncategorized";
        switch(cat) {
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
    public String apply(){
        
        /* Build task script */
        String task = "    - name: " + title + "\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - logging buffered " + buffer + " " + level + "\n\n";
        return task;
    }
    
    public void configure(boolean en) {
        enable(en);
        if (!en) {
            return;
        }
        Scanner selector = new Scanner(System.in);
        String choice = "";
        
        /* Set buffer size */        
        while (true) { 
            System.out.println("Enter the logging buffer size in bytes (4096-2147483647)");
            choice = selector.nextLine();
            try { 
                if ((Integer.parseInt(choice) >= 4096) && (Integer.parseInt(choice) <= 2147483647)) { // Input is in range
                    buffer = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Value not in range");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Value not in range");
            }
        }
        /* Set logging level */
        while (true) { 
            System.out.println("""
                               Enter logging level: 
                               0: emergencies
                               1: alerts
                               2: critical
                               3: errors
                               4: warnings
                               5: notifications
                               6: informational
                               7: debugging""");
            choice = selector.nextLine();
            try { 
                if ((Integer.parseInt(choice) >= 0) && (Integer.parseInt(choice) <= 7)) { // Input is in range
                    level = choice;
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
