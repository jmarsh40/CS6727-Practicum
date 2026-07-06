/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.gatech.stig.deploy;

import java.util.Scanner;

/**
 *
 * @author jmarsh40
 */
public class StigDeploy {

    private final String menu = """
                                Please select an option: 
                                1: Create a new configuration 
                                2: Exit"""; // Main menu text
    private Configuration config; // Current configuration

    public void main(String[] args) {
        Scanner selector = new Scanner(System.in); // Selector object to intake user input
        /* Main Menu loop*/
        while (true) {
            System.out.println(menu);
            String choice = selector.nextLine();
            if ("1".equals(choice)) { // Select 1 - create new configuration
                config = new Configuration();
                System.out.println("Creating new configuration...\n");
                config.editConfig();
            } else if ("2".equals(choice)) { // Select 3 - exit
                return;
            } else { // discard other input
                System.out.println("Please select one of the options below\n");
            }
        }
    }

    /* Load saved configurations */
//    private void loadConfig() {
//        System.out.println("Loading saved configuration\n");
//        config = new Configuration();
//        config.loadConfig();
//        config.editConfig();
//    }
}
