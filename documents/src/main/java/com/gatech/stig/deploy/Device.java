/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jmarsh40
 */
abstract public class Device {
    public String name; // device name
    public List<STIG> sList = new ArrayList<>(); // list of device-specific stigs
    private ACL[] aclList; // list of user-configure ACLs
    public String address; // IP address of device for SSH
    
    /* Return device name */
    public String getName() {
        return name;
    }
    
    /* Return device address */
    public String getAddress() {
        return address;
    }
    
    /* Return the device's list of STIGs */
    abstract public List<STIG> getStigs();
            
    /* Create an Ansible Playbook for the device configuration */
    abstract public void printStigs();
    
    /* Configure and toggle the STIGs for the device */
    abstract public void editDevice();
    
    /* Set the name of the device */
    public void setName(String in){
        name = in;
    }
    
    /* Set the address of the device */
    public void setAddress(String in){
        address = in;
    }
    
    /* Save STIG configurations */
    //public List<List<String>> save(){
    //    List<List<String>> out = new ArrayList<>();
    //    for (STIG stig : sList) {
    //        out.add(stig.save());
    //    }
    //    return out;
    //}
    
    /* Save STIG configurations */
    public List<String> save(){
        List<String> out = new ArrayList<>();
        Gson gson = new Gson();
        String jStig;
        //for (STIG stig : sList) {
        //    jStig = gson.toJson(stig);
        //    out.add(jStig);
        //    System.out.println(jStig);
        //}
        try {
        gson.toJson(sList, new FileWriter(name + ".save"));
        } catch (IOException e) {
            System.out.println("Cannot open file\n");
        }
        return out;
    }
}
