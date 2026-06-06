/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.gatech.stig.deploy;
import java.io.File;
import java.util.Scanner;

/**
 *
 * @author root
 */
public class StigDeploy {
    private static final String menu = "Please select an option: \nCreate a new configuration: 1 \nLoad a configuration: 2 \nExit: 3\n"; // Main menu text
    private static Configuration config; // Current configuration
    private static Configuration[] savedConfigs; // Saved configurations

    public static void main(String[] args) {
        Scanner selector = new Scanner(System.in); // Selector object to intake user input
        /* Main Menu loop*/
        while (true) {
            System.out.println(menu);
            String choice = selector.nextLine();
            /* Select 1 - create new configuration */
            if ("1".equals(choice)) {
                config = new Configuration();
            }
            /* Select 2 - load saved configuration */
            else if ("2".equals(choice)) {
                
            }
            /* Select 3 - exit */
            else if ("3".equals(choice)) {
                return;
            }
            /* discard other input */
            else {
                System.out.println("Please select one of the options above.");
            }       
        }
    }
    /* Load saved configurations */
    private static void loadConfigs() {
        // TBD
    }
}
