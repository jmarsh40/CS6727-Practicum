/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jmarsh40
 */
public class Configuration {

    public String name = "New Configuration"; // configuration name
    private final String menu = """
                          Please Select an option: 
                          1: Create New Device 
                          2: Edit Configured Devices 
                          3: Send Configuration to Ansible playbooks 
                          4: Return to main menu """;
    private final String dTypes = """
                            Possible Devices:
                            1: Cisco IOS Router 
                            2: Cisco IOL Switch 
                            3: Return to Configuration menu """;
    private List<Device> dList = new ArrayList<>(); // list of devices in configuration

    /* Edit the configuration */
    public void editConfig() {
        /* TBD */
        Scanner selector = new Scanner(System.in);

        while (true) {
            System.out.println(menu);
            String choice = selector.nextLine();

            /* Select 1 - Create a new device */
            if ("1".equals(choice)) {
                while (true) {
                    System.out.println("Please select one of the available device types:");
                    System.out.println(dTypes);
                    choice = selector.nextLine();
                    if ("1".equals(choice)) { // Configure new router
                        Device temp = new Router();
                        /* Get hostname and IP address */
                        System.out.println("Please enter a device name.");
                        String hostName = selector.nextLine();
                        temp.setName(hostName);
                        /* Configure the new device */
                        System.out.println("Configuring " + hostName);
                        temp.editDevice();
                        /* Add the new device to the configuration */
                        dList.add(temp);
                        break;
                    } else if ("2".equals(choice)) { // Configure new switch
                        Device temp = new Switch();
                        /* Get hostname and IP address */
                        System.out.println("Please enter a device name.");
                        String hostName = selector.nextLine();
                        temp.setName(hostName);
                        /* Configure the new device */
                        System.out.println("Configuring " + hostName);
                        temp.editDevice();
                        /* Add the new device to the configuration */
                        dList.add(temp);
                        break;
                    } else if ("3".equals(choice)) { // Return to Configuration menu
                        break;
                    } else { // Check for invalid input
                        System.out.println("Please select one of the available device options\n");
                    }
                }
            } else if ("2".equals(choice)) { // Select 2 - edit configured devices
                if (!dList.isEmpty()) {
                    System.out.println("Please select a configured device:");
                    int i = 1;
                    for (Device device : dList) {
                        System.out.println(i + ": " + device.getName());
                        i++;
                    }
                    choice = selector.nextLine();
                    i = Integer.parseInt(choice);
                    if ((i > 0) && (i <= dList.size())) { // check that selection is in bounds
                        dList.get(i-1).editDevice();
                    } else {
                        System.out.println("Please select one of the devices listed by number\n");
                    }
                } else {
                    System.out.println("There are no configured devices\n");
                }

            } else if ("3".equals(choice)) { // Select 3 - deploy configuration
                System.out.println("Building the inventory file...\n");
                buildInventory();
                System.out.println("Building Ansible playbooks...\n");
                deployConfig();
                System.out.println("Configuration successfully deployed!");
            } else if ("4".equals(choice)) { // Select 5 - exit
                return;
            } else { // Discard other input
                System.out.println("Please select one of the options below\n");
            }
        }
    }

    /* Save the configuration */
//    public void saveConfig() {
//        /* TBD */
//        /* Check for empty device list */
//        if ((dList == null) || dList.isEmpty()) {
//            System.out.println("There are no configured devices\n");
//            return;
//        }
//        String fileName = "save.json";
////        File jFile = new File(fileName);
////        List<List<String>> out = new ArrayList<>();
////        for (Device device : dList) {
//        //fileName = device.getName() + ".json";
//        //jFile = new File(fileName + ".json");
////            out.add(device.save());
//
//        Gson gson = new Gson();
//        try {
//            gson.toJson(dList, new FileWriter(fileName));
//        } catch (IOException e) {
//            System.out.println("Could not write to file\n");
//        }
//    }
    
//    public void loadConfig(){
//        String fileName = "save.json";
//        Gson gson = new Gson();
//        try {
//        Reader read = new FileReader(fileName);
//        Type savedObjects = new TypeToken<ArrayList<Device>>() {}.getType();
//        dList = gson.fromJson(read, savedObjects);
//        } catch (IOException e) {
//            System.out.println("Could not open file\n");
//        }
//    }

    /* Deploy the configuration */
    public void deployConfig() {
        /* Check for empty device list */
        if (dList.isEmpty()) {
            System.out.println("There are no configured devices\n");
            return;
        }
        /* Create a playbook for each device */
        for (Device inv : dList) {
            String name = inv.getName();
            String fileName = name + ".yml";
            try {
                /* Create a new playbook file for each device */
                File iFile = new File(fileName);
                if (iFile.createNewFile()) {
                    System.out.println("Created " + fileName);
                } else {
                    System.out.println(fileName + " already exists");
                }

                String aTemplate = "- name: \"STIG Deployment\"\n"
                        + "  hosts: " + name + "\n"
                        + "  ignore_errors: true\n"
                        + "  tasks: \n\n"
                        + "    ### Gather Data ###\n\n"
                        + "    - name: Gather Data\n"
                        + "      cisco.ios.ios_facts:\n\n"
                        + "    ### Begin benchmarks ###\n\n";
                /* Write header info to playbook */
                Path iPath = Paths.get(fileName);
                Files.writeString(iPath, aTemplate);
                /* Write STIGs to playbook */
                for (STIG stig : inv.getStigs()) {
                    if (stig.enabled) {
                        String tasks = stig.apply();
                        Files.writeString(iPath, tasks, StandardOpenOption.APPEND);
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occured attempting to create the " + name + " playbook");
                e.printStackTrace();
            }
        }
    }

    /* Build Inventory File */
    public void buildInventory() {
        String fileName = "inventory.ini";
        File iFile = new File(fileName);
        try {
            /* Create a new inventory file */
            if (iFile.createNewFile()) {
                System.out.println("Created inventory file");
            } else {
                System.out.println("Inventory file already exists");
            }

            /* Ansible connection variables */
            String script = """
                            [all:vars]
                            ansible_connection = ansible.netcommon.network_cli
                            ansible_network_os = cisco.ios.ios
                            ansible_become = true
                            ansible_become_password = cisco
                            ansible_user = cisco
                            ansible_password = password
                            
                            """;
            /* Add devices and addresses */
            for (Device inv : dList) {
                script += "[" + inv.getName() + "]\n"
                        + inv.getAddresses();
            }
            /* Write inventory to file */
            Path iPath = Paths.get(fileName);
            Files.writeString(iPath, script);
        } catch (IOException e) {
            System.out.println("An error occured attempting to create the inventory file");
            e.printStackTrace();
        }
    }
}
