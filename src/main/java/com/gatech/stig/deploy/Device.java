/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jmarsh40
 */
abstract public class Device {
    public String name; // device name
    public List<STIG> sList = new ArrayList<>(); // list of device-specific stigs
    public List<String> addresses = new ArrayList<>(); // IP address of device for SSH
    
    /* Return device name */
    public String getName() {
        return name;
    }
    
    /* Return device address */
    public String getAddresses() {
        String address = "";
        if (addresses != null) {
            for (String add : addresses) {
                address += add + "\n";
            }
        } else {
            address = "There are no configured addresses for this device";
        }
        return address;
    }
    
    /* Return the device's list of STIGs */
    abstract public List<STIG> getStigs();
    
    /* Configure and toggle the STIGs for the device */
    abstract public void editDevice();
    
    /* Set the name of the device */
    public void setName(String in){
        name = in;
    }
    
    /* Set the device addresses */
    public void addAddress(){
        Scanner selector = new Scanner(System.in);
        String choice = "";
        
        /* Add IP addresses until finished */
        while (true) {
            System.out.println("Please enter an IP address. When finished type \"done\"");
            choice = selector.nextLine();
            /* Check for valid address */
            if (IP.check(choice)) {
                /* Check for duplicate address */
                if (addresses.contains(choice)) {
                    System.out.println(choice + " is already in the address list");
                } else {
                    System.out.println("Adding " + choice + " to address list\n");
                    addresses.add(choice);
                }
            } else if ("done".equals(choice)) {
                return;
            } else {
                System.out.println(choice + " is not a valid address");
            }
        }
    }
    
    /* Load the switch-specific STIGs */
    abstract public void loadStigs();
    
    /* Save STIG configurations */
//    public List<List<String>> save(){
//        List<List<String>> out = new ArrayList<>();
//        for (STIG stig : sList) {
//            out.add(stig.save());
//        }
//        return out;
//    }
    
    /* Save STIG configurations */
//    public List<String> save(){
//        List<String> out = new ArrayList<>();
//        Gson gson = new Gson();
//        String jStig;
//        //for (STIG stig : sList) {
//        //    jStig = gson.toJson(stig);
//        //    out.add(jStig);
//        //    System.out.println(jStig);
//        //}
//        try {
//        gson.toJson(sList, new FileWriter(name + ".save"));
//        } catch (IOException e) {
//            System.out.println("Cannot open file\n");
//        }
//        return out;
//    }
}
