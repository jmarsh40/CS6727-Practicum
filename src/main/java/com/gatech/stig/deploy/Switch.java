/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy;

import com.gatech.stig.deploy.stigs.iosswitch.*;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jmarsh40
 */
public class Switch extends Device {
    private String menu = "Please select an option: \n"
            + "1: List STIGs\n"
            + "2: Enable/Disable STIGs\n"
            + "3: Change name\n"
            + "4: Add IP addresses\n"
            + "5: Enable all STIGs with default values\n"
            + "6: Configure all STIGs:\n"
            + "7: Return to Configuration menu";

    /* Constructor to load STIGs */
    public Switch() {
        loadStigs();
    }
    
    /* Return the device's list of STIGs */
    public List<STIG> getStigs(){
            return sList;
    }
    
    /* Configure the switch STIGs */
    public void editDevice() {
        Scanner selector = new Scanner(System.in);

        while (true) {
            System.out.println("Device name: " + name);
            System.out.println("Device addresses: \n" 
                    + getAddresses());
            System.out.println(menu);
            String choice = selector.nextLine();
            if ("1".equals(choice)) { // Select 1 - List STIGs
                if (sList.isEmpty()){
                    System.out.println("There are no STIGs configured for this device");
                }
                else {
                    int cat1=0, cat1e = 0; // category 1 stigs
                    int cat2=0, cat2e = 0; // category 2 stigs
                    int cat3=0, cat3e = 0; // category 3 stigs
                    for (STIG stig : sList) {
                        System.out.println(stig.getInfo());
                        switch (stig.getCat()) {
                            case 1:
                                cat1++;
                                if (stig.enabled) {
                                    cat1e++;
                                }   break;
                            case 2:
                                cat2++;
                                if (stig.enabled) {
                                    cat2e++;
                                }   break;
                            case 3:
                                cat3++;
                                if (stig.enabled) {
                                    cat3e++;
                                }   break;
                            default:
                                break;
                        }
                    }
                    System.out.println("Current compliance - Enabled/Total:\n"
                            + cat1e + "/" + cat1 + "\n"
                            + cat2e + "/" + cat2 + "\n"
                            + cat3e + "/" + cat3 + "\n\n");
                }
            } else if ("2".equals(choice)) { // Select 2 - Enable/Disable STIGs
                if (sList.isEmpty()){
                    System.out.println("There are no STIGs configured for this device");
                }
                else {
                    int i = 1;
                    /* List STIGs */
                    System.out.println("Select a STIG benchmark: ");
                    for (STIG stig : sList) {
                        System.out.println(i + ": " + stig.getInfo());
                        i++;
                    }
                    /* Select one of the listed STIGs */
                    choice = selector.nextLine();
                    try {
                        i = Integer.parseInt(choice);
                        if ((i > 0) && (i <= sList.size())) {
                            while (true) {
                                System.out.println("1: Enable \n2: Disable");
                                choice = selector.nextLine();
                                if ("1".equals(choice)) {
                                    sList.get(i - 1).configure(true);
                                    break;
                                } else if ("2".equals(choice)) {
                                    sList.get(i - 1).configure(false);
                                    break;
                                } else {
                                    System.out.println("Please select one of the listed options");
                                }
                            }
                            sList.get(i - 1).apply();
                        } else {
                            System.out.println("Please select one of the STIGs listed by number\n");
                        }
                    } catch (NumberFormatException e) { // Catch non-parseable input
                        System.out.println("Please select one of the STIGs listed by number\n");
                    }

                }
            } else if ("3".equals(choice)) { // Select 3 - Change name
                System.out.println("Enter the new device name");
                choice = selector.nextLine();
                name = choice;
            } else if ("4".equals(choice)) { // Select 4 - Add addresses
                addAddress();
            } else if ("5".equals(choice)) { // Select 5 - Enable all STIGs for testing
                for (STIG stig : sList) {
                    System.out.println("!!! Enabling all STIGs !!!");
                    stig.enable(true);
                }
            } else if ("6".equals(choice)) { // Select 6 - Configure all STIGs for testing
                for (STIG stig : sList) {
                    System.out.println("!!! Configuring all STIGs !!!");
                    stig.configure(true);
                }
            } else if ("7".equals(choice)) { // Select 7 - Return to Configuration menu
                break;
            } else { // Discard other input
                System.out.println("Please select one of the available device options:");
            }
        }
    }
    /* Load the switch-specific STIGs */
    public void loadStigs() {
        /* TBD */
        sList.add(new CISC_ND_000010());
//        sList.add(new CISC_ND_000090());
//        sList.add(new CISC_ND_000100());
//        sList.add(new CISC_ND_000110());
//        sList.add(new CISC_ND_000120());
//        sList.add(new CISC_ND_000140());
//        sList.add(new CISC_ND_000150());
//        sList.add(new CISC_ND_000160());
//        sList.add(new CISC_ND_000210());
//        sList.add(new CISC_ND_000280());
//        sList.add(new CISC_ND_000290());
//        sList.add(new CISC_ND_000330());
//        sList.add(new CISC_ND_000380());
//        sList.add(new CISC_ND_000390());
//        sList.add(new CISC_ND_000460());
//        sList.add(new CISC_ND_000470());
//        sList.add(new CISC_ND_000490());
//        sList.add(new CISC_ND_000550());
//        sList.add(new CISC_ND_000570());
//        sList.add(new CISC_ND_000580());
//        sList.add(new CISC_ND_000590());
//        sList.add(new CISC_ND_000600());
//        sList.add(new CISC_ND_000610());
//        sList.add(new CISC_ND_000620());
//        sList.add(new CISC_ND_000720());
//        sList.add(new CISC_ND_000880());
//        sList.add(new CISC_ND_000980());
//        sList.add(new CISC_ND_001000());
//        sList.add(new CISC_ND_001030());
//        sList.add(new CISC_ND_001130());
//        sList.add(new CISC_ND_001140());
//        sList.add(new CISC_ND_001150());
//        sList.add(new CISC_ND_001200());
//        sList.add(new CISC_ND_001210());
//        sList.add(new CISC_ND_001250());
//        sList.add(new CISC_ND_001260());
//        sList.add(new CISC_ND_001270());
//        sList.add(new CISC_ND_001370());
//        sList.add(new CISC_ND_001410());
//        sList.add(new CISC_ND_001440());
//        sList.add(new CISC_ND_001450());
//        sList.add(new CISC_ND_001470());
    }
    /* Print STIG information */
    public void printStigs() {
        /* TBD */
    }
}
