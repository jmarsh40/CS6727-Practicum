/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy;

import com.gatech.stig.deploy.stigs.router.*;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jmarsh40
 */
public class Router extends Device{
    private String menu = "Please select an option: \n"
            + "List STIGs: 1 \n"
            + "Enable/Disable STIGs: 2 \n"
            + "Change name:  3 \n"
            + "Change address: 4 \n"
            + "Return to Configuration menu: 5";
    
    /* Constructor to load STIGs */
    public Router() {
        loadStigs();
        /* TBD */
    }
    
        /* Return the device's list of STIGs */
    public List<STIG> getStigs(){
            /* TBD */
            return sList;
    }
    
    /* Configure the Router STIGs */
    public void editDevice() {
        /* TBD */
        Scanner selector = new Scanner(System.in);

        while (true) {
            System.out.println("Device name: " + name);
            System.out.println("Device address: " + address);
            System.out.println(menu);
            String choice = selector.nextLine();
            if ("1".equals(choice)) { // Select 1 - List STIGs
                if (sList.size() == 0){
                    System.out.println("There are no STIGs configured for this device");
                }
                else {
                    for (STIG stig : sList) {
                        System.out.println(stig.getInfo());
                    }
                }
            } else if ("2".equals(choice)) { // Select 2 - Enable/Disable STIGs
                if (sList.size() == 0){
                    System.out.println("There are no STIGs configured for this device");
                }
                else {
                    int i = 1;
                    System.out.println("Select a STIG benchmark: ");
                    for (STIG stig : sList) {
                        System.out.println(i + ": " + stig.getInfo());
                        i++;
                    }
                    choice = selector.nextLine();
                    i = Integer.parseInt(choice);
                    if ((i > 0) && (i <= sList.size())) {
                        System.out.println("!!!Applying STIG!!!\n");
                        sList.get(i-1).apply();
                    } else {
                        System.out.println("Please select one of the STIGs listed by number\n");
                    }
                }
            } else if ("3".equals(choice)) { // Select 3 - Change name
                System.out.println("Enter the new device name");
                choice = selector.nextLine();
                name = choice;
                System.out.println("Device name: " + name);
            } else if ("4".equals(choice)) { // Select 4 - Change address
                System.out.println("Enter the new device address");
                choice = selector.nextLine();
                address = choice;
                System.out.println("Device address: " + address);
            } else if ("5".equals(choice)) { // Select 5 - Return to Configuration menu
                break;
            } else { // Discard other input
                System.out.println("Please select one of the available device options:");
            }
        }
        
    }
    /* Load the router-specific STIGs */
    public void loadStigs() {
        /* TBD */
        sList.add(new CISC_ND_000150());
    }
    /* Print STIG information */
    public void printStigs() {
        /* TBD */
    }
}
